package com.restdemo.producer.handler;

import com.example.common.pojo.RpcRequest;
import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageHeader;
import com.example.common.protocol.MessageProtocol;
import com.example.common.protocol.MsgStatus;
import com.example.common.protocol.MsgType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lindzhao
 */
public class RpcRequestHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcRequest>> {
    private static final Logger logger = LoggerFactory.getLogger(RpcRequestHandler.class);

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcRequest> rpcRequestMessageProtocol) throws Exception {
        threadPoolExecutor.submit(() -> {
            MessageProtocol<RpcResponse> rpcResponseMessageProtocol = new MessageProtocol<>();
            RpcResponse rpcResponse = new RpcResponse();
            MessageHeader header = rpcRequestMessageProtocol.getHeader();
            RpcRequest rpcRequest = rpcRequestMessageProtocol.getBody();
            header.setMsgType(MsgType.RESPONSE.getType());
            try {
                Object result = handle(rpcRequest);
                rpcResponse.setData(result);
                header.setStatus(MsgStatus.SUCCESS.getCode());
                rpcResponseMessageProtocol.setHeader(header);
                rpcResponseMessageProtocol.setBody(rpcResponse);
            } catch (Throwable e) {
                header.setStatus(MsgStatus.FAIL.getCode());
                rpcResponse.setMessage(e.toString());
                logger.error("process request {} error", header.getRequestId(), e);
            }
            channelHandlerContext.writeAndFlush(rpcResponseMessageProtocol);
        });
    }

    private Object handle(RpcRequest request) {
        //TODO
        return null;
    }
}
