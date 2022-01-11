package com.restdemo.rpc.api;

/**
 * @author lindzhao
 */
public interface GreetingService {
    /**
     * greet method
     * @param name name
     * @return greetings
     */
    String greet(String name);
}
