package com.example.common.serialization;

/**
 * @author lindzhao
 */

public enum SerializationTypeEnum {
    HESSIAN((byte) 0),
    JSON((byte) 1);

    private byte type;

    SerializationTypeEnum(byte type) {
        this.type = type;
    }

    public static SerializationTypeEnum parseByName(String typeName) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.name().equalsIgnoreCase(typeName)) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

    public byte getType() {
        return type;
    }

}
