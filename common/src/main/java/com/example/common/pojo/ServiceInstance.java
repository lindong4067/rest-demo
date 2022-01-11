package com.example.common.pojo;


/**
 * @author lindzhao
 */
public class ServiceInstance {
    private String instanceId;
    private String serviceName;

    public ServiceInstance(String instanceId, String serviceName) {
        this.instanceId = instanceId;
        this.serviceName = serviceName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
