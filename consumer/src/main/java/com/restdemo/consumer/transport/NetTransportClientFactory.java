package com.restdemo.consumer.transport;

/**
 * @author lindzhao
 */
public class NetTransportClientFactory {
    public static NetTransportClient getNetClientTransport(){
        return new NettyNetTransportClient();
    }
}
