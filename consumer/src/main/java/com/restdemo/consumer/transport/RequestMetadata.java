package com.restdemo.consumer.transport;

import com.example.common.pojo.RpcRequest;
import com.example.common.protocol.MessageProtocol;

import java.io.Serializable;

/**
 * @author lindzhao
 */
public class RequestMetadata implements Serializable {
    private MessageProtocol<RpcRequest> protocol;
    private String address;
    private Integer port;
    private Integer timeout;

    public MessageProtocol<RpcRequest> getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol<RpcRequest> protocol) {
        this.protocol = protocol;
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

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
