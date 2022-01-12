package com.example.common.protocol;

/**
 * @author lindzhao
 */

public enum MsgStatus {
    SUCCESS((byte)0),
    FAIL((byte)1);

    private final byte code;

    MsgStatus(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code) {
        return MsgStatus.SUCCESS.code == code;
    }

    public byte getCode() {
        return code;
    }
}
