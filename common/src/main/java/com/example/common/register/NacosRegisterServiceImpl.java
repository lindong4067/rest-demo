package com.example.common.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author lindzhao
 */
public class NacosRegisterServiceImpl implements RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(NacosRegisterServiceImpl.class);

    private String nacosAddr;
    private String namespace;
    private String ip;
    private int port;

    public NacosRegisterServiceImpl(String nacosAddr, String namespace, String ip, int port) {
        this.nacosAddr = nacosAddr;
        this.namespace = namespace;
        this.ip = ip;
        this.port = port;
    }

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
