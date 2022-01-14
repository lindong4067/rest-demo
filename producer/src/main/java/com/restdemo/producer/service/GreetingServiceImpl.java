package com.restdemo.producer.service;

import com.restdemo.producer.annotation.RpcService;
import com.restdemo.rpc.api.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author lindzhao
 */
@RpcService(interfaceType = GreetingService.class)
@Service
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
    public String greet(String name) {
        logger.info("Hello, {}!", name);
        return "Hello, " + name + "!";
    }
}
