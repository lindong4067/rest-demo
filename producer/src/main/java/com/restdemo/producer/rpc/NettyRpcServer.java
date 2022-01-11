package com.restdemo.producer.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lindzhao
 */
public class NettyRpcServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServer.class);
    private String serverAddress;
    private int port;
    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public NettyRpcServer(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Override
    public void start() {
        bootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder());
                            //todo
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(serverAddress, port).sync();
            logger.info("server addr {} started on port {}", serverAddress, port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("netty server start failed.", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        // todo
    }
}
