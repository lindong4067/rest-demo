package com.example.common.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author lindzhao
 */
public class JsonSerialization implements RpcSerialization {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper();
    }

    private static ObjectMapper generateMapper() {
        ObjectMapper customMapper = new ObjectMapper();

        customMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }

    @Override
    public <T> byte[] serialize(T body) throws IOException {
        return body instanceof String ? ((String) body).getBytes() : MAPPER.writeValueAsString(body).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return MAPPER.readValue(Arrays.toString(data), clz);
    }
}
