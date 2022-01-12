package com.example.common.protocol;

import java.io.Serializable;

/**
 * @author lindzhao
 */
public class MessageProtocol<T> implements Serializable {
    private MessageHeader header;
    private T body;

    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
