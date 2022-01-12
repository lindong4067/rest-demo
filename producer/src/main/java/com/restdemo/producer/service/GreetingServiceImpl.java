package com.restdemo.producer.service;

import com.restdemo.producer.annotation.RpcService;
import com.restdemo.rpc.api.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lindzhao
 */
@RpcService(interfaceType = GreetingService.class)
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
