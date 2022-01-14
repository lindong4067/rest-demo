package com.example.common.pojo;


/**
 * @author lindzhao
 */
public class ServiceInstance {
    private String instanceId;
    private String serviceName;
    private String address;
    private Integer port;

    public ServiceInstance(String instanceId, String serviceName) {
        this.instanceId = instanceId;
        this.serviceName = serviceName;
    }

    public ServiceInstance(String instanceId, String serviceName, String address, Integer port) {
        this.instanceId = instanceId;
        this.serviceName = serviceName;
        this.address = address;
        this.port = port;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
