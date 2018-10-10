/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.example.jdbc.main.orche.yaml.etcd;

import io.shardingsphere.example.repository.jdbc.repository.OrderItemRepository;
import io.shardingsphere.example.repository.jdbc.repository.OrderRepository;
import io.shardingsphere.example.repository.jdbc.service.DemoService;
import io.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationMasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.orchestration.internal.datasource.OrchestrationMasterSlaveDataSource;

import javax.sql.DataSource;
import java.io.File;

public class MasterSlaveOnly {
    
    private static final boolean LOAD_CONFIG_FROM_REG_CENTER = false;
    
    public static void main(final String[] args) throws Exception {
        DataSource dataSource = YamlOrchestrationMasterSlaveDataSourceFactory.createDataSource(getYamlFile());
        new DemoService(new OrderRepository(dataSource), new OrderItemRepository(dataSource)).demo();
        ((OrchestrationMasterSlaveDataSource) dataSource).close();
    }
    
    private static File getYamlFile() {
        String path = LOAD_CONFIG_FROM_REG_CENTER ? "/META-INF/orche/etcd/cloud/master-slave.yaml" : "/META-INF/orche/etcd/local/master-slave.yaml";
        return new File(MasterSlaveOnly.class.getResource(path).getFile());
    }
}
