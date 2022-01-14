package com.restdemo.producer.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author lindzhao
 */
@Component
public class NacosRegisterServiceImpl implements RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(NacosRegisterServiceImpl.class);
    @Value("${nacos.addr}")
    private String nacosAddr;
    @Value("${nacos.namespace}")
    private String namespace;
    @Value("${rpc.ip}")
    private String ip;
    @Value("${rpc.port}")
    private int port;

    @Override
    public void register(String serviceName) {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosAddr);
            properties.setProperty("namespace", namespace);
            NamingService naming = NamingFactory.createNamingService(properties);
            naming.registerInstance(serviceName, ip, port);
        } catch (NacosException e) {
            logger.error("service register failed.", e);
        }
    }

    @Override
    public void unregister(String serviceName) {
        //todo where is unregister
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosAddr);
            properties.setProperty("namespace", namespace);
            NamingService naming = NamingFactory.createNamingService(properties);
            naming.deregisterInstance(serviceName, ip, port);
        } catch (NacosException e) {
            logger.error("service unregister failed.", e);
        }
    }
}
