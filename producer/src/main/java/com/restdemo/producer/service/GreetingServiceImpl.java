package com.restdemo.producer.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.restdemo.rpc.api.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

/**
 * @author lindzhao
 */
@Service
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    @PreDestroy
    public void unregister() {

    }
}
