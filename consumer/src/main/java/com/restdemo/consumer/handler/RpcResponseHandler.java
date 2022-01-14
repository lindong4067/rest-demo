package com.restdemo.consumer.handler;

import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;
import com.restdemo.consumer.cache.LocalRpcResponseCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lindzhao
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcResponse> rpcResponseMessageProtocol) throws Exception {
        String requestId = rpcResponseMessageProtocol.getHeader().getRequestId();
        LocalRpcResponseCache.fillResponse(requestId, rpcResponseMessageProtocol);
    }
}
