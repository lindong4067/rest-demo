package com.restdemo.consumer.transport;

import com.example.common.codec.RpcDecoder;
import com.example.common.codec.RpcEncoder;
import com.example.common.pojo.RpcRequest;
import com.example.common.pojo.RpcResponse;
import com.example.common.protocol.MessageProtocol;
import com.restdemo.consumer.cache.LocalRpcResponseCache;
import com.restdemo.consumer.handler.RpcResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author lindzhao
 */
public class NettyNetTransportClient implements NetTransportClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyNetTransportClient.class);

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
        MessageProtocol<RpcRequest> protocol = metadata.getProtocol();
        RpcFuture<MessageProtocol<RpcResponse>> future = new RpcFuture<>();
        LocalRpcResponseCache.add(protocol.getHeader().getRequestId(), future);
        ChannelFuture channelFuture = bootstrap.connect(metadata.getAddress(), metadata.getPort()).sync();
        channelFuture.addListener(future1 -> {
            if (channelFuture.isSuccess()) {
                logger.info("connect rpc server {} on port {} success.", metadata.getAddress(), metadata.getPort());
            } else {
                logger.error("connect rpc server {} on port {} failed.", metadata.getAddress(), metadata.getPort());
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        channelFuture.channel().writeAndFlush(protocol);
        return metadata.getTimeout() != null ? future.get(metadata.getTimeout(), TimeUnit.MILLISECONDS) : future.get();
    }
}
