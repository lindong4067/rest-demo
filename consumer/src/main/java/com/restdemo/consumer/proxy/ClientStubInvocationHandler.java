package com.restdemo.consumer.proxy;

import com.example.common.discovery.DiscoveryService;
import com.example.common.exception.ResourceNotFoundException;
import com.example.common.pojo.RpcRequest;
import com.example.common.pojo.ServiceInstance;
import com.example.common.protocol.MessageHeader;
import com.example.common.protocol.MessageProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lindzhao
 */
public class ClientStubInvocationHandler implements InvocationHandler {

    private DiscoveryService discoveryService;
    private Class<?> clazz;

    public ClientStubInvocationHandler(DiscoveryService discoveryService, Class<?> clz) {
        this.discoveryService = discoveryService;
        this.clazz = clz;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        ServiceInstance serviceInstance = discoveryService.discoveryOneHealthyInstance(this.clazz.getName());
        if (serviceInstance == null) {
            throw new ResourceNotFoundException("service not found.");
        }
        MessageProtocol<RpcRequest> messageProtocol = new MessageProtocol<>();
        //todo Rpc client config properties
        MessageHeader messageHeader = MessageHeader.build("JSON");
        RpcRequest request = new RpcRequest();
        request.setServiceName(this.clazz.getName());
        request.setMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(objects);

        //todo sendRequest()
        return null;
    }
}
