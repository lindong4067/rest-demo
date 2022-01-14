package com.restdemo.consumer.cache;

import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;
import com.restdemo.consumer.transport.RpcFuture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lindzhao
 */
public class LocalRpcResponseCache {

    private static final Map<String, RpcFuture<MessageProtocol<RpcResponse>>> responseCache = new HashMap<>();

    public static void add(String requestId, RpcFuture<MessageProtocol<RpcResponse>> future) {
        responseCache.put(requestId, future);
    }

    public static void fillResponse(String requestId, MessageProtocol<RpcResponse> future) {
        RpcFuture<MessageProtocol<RpcResponse>> rpcFuture = responseCache.get(requestId);
        rpcFuture.setResponse(future);
        responseCache.remove(requestId);
    }
}
