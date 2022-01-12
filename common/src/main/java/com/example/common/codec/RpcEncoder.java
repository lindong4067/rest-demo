package com.example.common.codec;

import com.example.common.protocol.MessageHeader;
import com.example.common.protocol.MessageProtocol;
import com.example.common.serialization.RpcSerialization;
import com.example.common.serialization.SerializationFactory;
import com.example.common.serialization.SerializationTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author lindzhao
 */
public class RpcEncoder<T> extends MessageToByteEncoder<MessageProtocol<T>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol<T> messageProtocol, ByteBuf byteBuf) throws Exception {
        MessageHeader header = messageProtocol.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getSerialization());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeCharSequence(header.getRequestId(), StandardCharsets.UTF_8);
        SerializationTypeEnum typeEnum = SerializationTypeEnum.parseByType(header.getSerialization());
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(typeEnum);
        byte[] data = rpcSerialization.serialize(messageProtocol.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
