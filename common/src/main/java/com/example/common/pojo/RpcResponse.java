package com.example.common.pojo;

import java.io.Serializable;

/**
 * @author lindzhao
 */
public class RpcResponse implements Serializable {
    private Object data;
    private String message;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
