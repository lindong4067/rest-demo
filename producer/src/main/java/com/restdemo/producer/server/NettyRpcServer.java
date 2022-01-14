package com.restdemo.producer.server;

import com.example.common.codec.RpcDecoder;
import com.example.common.codec.RpcEncoder;
import com.restdemo.producer.handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author lindzhao
 */
@Component
public class NettyRpcServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServer.class);
    @Value("${rpc.ip:10.242.28.209}")
    private String serverAddress;
    @Value("${rpc.port:28081}")
    private int port;
    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

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
                                    .addLast(new RpcEncoder<>())
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcRequestHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(serverAddress, port).sync();
            logger.info("server addr {} started on port {}", serverAddress, port);
        } catch (Exception e) {
            logger.error("netty server start failed.", e);
        }
    }

    @Override
    public void stop() {
        try {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            logger.info("server addr {} stopped on port {}", serverAddress, port);
        } catch (Exception e) {
            logger.error("netty server stop failed.", e);
        }
    }

    @PostConstruct
    public void init() {
        start();
    }

    @PreDestroy
    public void destroy() {
        stop();
    }
}
