package com.restdemo.consumer.proxy;

import com.restdemo.consumer.discovery.DiscoveryService;
import com.example.common.exception.ResourceNotFoundException;
import com.example.common.exception.RpcException;
import com.example.common.pojo.RpcRequest;
import com.example.common.pojo.RpcResponse;
import com.example.common.pojo.ServiceInstance;
import com.example.common.protocol.MessageHeader;
import com.example.common.protocol.MessageProtocol;
import com.example.common.protocol.MsgStatus;
import com.restdemo.consumer.transport.NetTransportClientFactory;
import com.restdemo.consumer.transport.RequestMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lindzhao
 */
public class ClientStubInvocationHandler implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(ClientStubInvocationHandler.class);

    private DiscoveryService discoveryService;
    private Class<?> clazz;

    private String serialization = "hessian";

    private Integer timeout = 10000;

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
        MessageHeader messageHeader = MessageHeader.build(serialization);
        RpcRequest request = new RpcRequest();
        request.setServiceName(this.clazz.getName());
        request.setMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(objects);
        messageProtocol.setHeader(messageHeader);
        messageProtocol.setBody(request);

        RequestMetadata metadata = new RequestMetadata();
        metadata.setProtocol(messageProtocol);
        metadata.setAddress(serviceInstance.getAddress());
        metadata.setPort(serviceInstance.getPort());
        metadata.setTimeout(timeout);

        MessageProtocol<RpcResponse> responseMessageProtocol = NetTransportClientFactory.getNetClientTransport().sendRequest(metadata);
        if (responseMessageProtocol == null) {
            logger.error("request timeout.");
            throw new RpcException("request timeout.");
        }
        if (!MsgStatus.isSuccess(responseMessageProtocol.getHeader().getStatus())) {
            logger.error("Rpc invoke failed. {}", responseMessageProtocol.getBody().getMessage());
            throw new RpcException("Rpc invoke failed.");
        }
        return responseMessageProtocol.getBody().getData();
    }
}
