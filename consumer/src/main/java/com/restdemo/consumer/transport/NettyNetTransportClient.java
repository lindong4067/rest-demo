package com.restdemo.consumer.transport;

import com.example.common.codec.RpcDecoder;
import com.example.common.codec.RpcEncoder;
import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;
import com.restdemo.consumer.handler.RpcResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lindzhao
 */
public class NettyNetTransportClient implements NetTransportClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public NettyNetTransportClient() {
        this.bootstrap = new Bootstrap();
        this.eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new RpcDecoder())
                                .addLast(new RpcResponseHandler())
                                .addLast(new RpcEncoder<>());
                    }
                });
    }

    @Override
    public MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception {
        // todo
        return null;
    }
}
