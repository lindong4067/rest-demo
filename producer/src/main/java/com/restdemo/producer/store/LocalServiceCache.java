package com.restdemo.producer.store;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lindzhao
 */
public final class LocalServiceCache {
    private static final Map<String, Object> serviceCacheMap = new HashMap<>();

    public static void store(String serviceName, Object bean) {
        serviceCacheMap.merge(serviceName, bean, (oldValue, newValue) -> newValue);
    }

    public static Object get(String serviceName) {
        return serviceCacheMap.get(serviceName);
    }
}
