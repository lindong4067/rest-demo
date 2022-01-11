package com.example.common.discovery;

import com.example.common.pojo.ServiceInstance;

import java.util.List;

/**
 * @author lindzhao
 */
public interface DiscoveryService {

    List<ServiceInstance> discovery(String serviceName);

    ServiceInstance discoveryOneHealthyInstance(String serviceName);
}
