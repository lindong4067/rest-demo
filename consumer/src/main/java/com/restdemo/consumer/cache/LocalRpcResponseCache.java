package com.restdemo.consumer.cache;

import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author lindzhao
 */
public class LocalRpcResponseCache {

    private static Map<String, CompletableFuture<MessageProtocol<RpcResponse>>> responseCache = new HashMap<>();

    public static void add(String requestId, CompletableFuture<MessageProtocol<RpcResponse>> future) {
        responseCache.put(requestId, future);
    }

    public static void fillResponse(String requestId, CompletableFuture<MessageProtocol<RpcResponse>> future) {
        CompletableFuture<MessageProtocol<RpcResponse>> completableFuture = responseCache.get(requestId);
//        completableFuture
    }
}
