package com.restdemo.consumer.transport;

import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;

/**
 * @author lindzhao
 */
public interface NetTransportClient {
    MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception;
}
