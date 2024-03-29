package com.restdemo.consumer.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.example.common.pojo.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lindzhao
 */
@Component
public class NacosDiscoveryServiceImpl implements DiscoveryService {
    private static final Logger logger = LoggerFactory.getLogger(NacosDiscoveryServiceImpl.class);
    @Value("${nacos.addr}")
    private String nacosAddr = "127.0.0.1:8848";
    @Value("${nacos.namespace}")
    private String namespace = "public";

    @Override
    public List<ServiceInstance> discovery(String serviceName) {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosAddr);
            properties.setProperty("namespace", namespace);
            NamingService naming = NamingFactory.createNamingService(properties);
            List<Instance> allInstances = naming.getAllInstances(serviceName);
            if (allInstances != null && !allInstances.isEmpty()) {
                List<ServiceInstance> instanceList = new ArrayList<>();
                for (Instance instance : allInstances) {
                    if (instance.isHealthy()) {
                        instanceList.add(new ServiceInstance(instance.getInstanceId(), instance.getClusterName()));
                    }
                }
                return instanceList;
            }
        } catch (NacosException e) {
            logger.error("service register failed.", e);
        }
        return new ArrayList<>();
    }

    @Override
    public ServiceInstance discoveryOneHealthyInstance(String serviceName) {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosAddr);
            properties.setProperty("namespace", namespace);
            NamingService naming = NamingFactory.createNamingService(properties);
            Instance instance = naming.selectOneHealthyInstance(serviceName);
            if (instance != null) {
                return new ServiceInstance(instance.getInstanceId(), instance.getServiceName(), instance.getIp(), instance.getPort());
            }
        } catch (NacosException e) {
            logger.error("service register failed.", e);
        }
        return null;
    }
}
