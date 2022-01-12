package com.example.common.serialization;

import java.io.IOException;

/**
 * @author lindzhao
 */
public interface RpcSerialization {
    <T> byte[] serialize(T body) throws IOException;
    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}
