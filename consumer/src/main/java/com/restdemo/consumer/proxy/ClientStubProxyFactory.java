package com.restdemo.consumer.proxy;

import com.example.common.discovery.DiscoveryService;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lindzhao
 */
public class ClientStubProxyFactory {

    private Map<Class<?>, Object> objectMap = new HashMap<>();

    public <T> T getProxy(Class<T> type, DiscoveryService discoveryService) {
        return (T) objectMap.computeIfAbsent(type, clazz ->
                Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ClientStubInvocationHandler(discoveryService, clazz))
        );
    }

}
