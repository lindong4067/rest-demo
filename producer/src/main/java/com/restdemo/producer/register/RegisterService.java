package com.restdemo.producer.register;

/**
 * @author lindzhao
 */
public interface RegisterService {

    void register(String serviceName);

    void unregister(String serviceName);
}
