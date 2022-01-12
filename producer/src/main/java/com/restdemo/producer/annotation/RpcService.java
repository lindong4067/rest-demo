package com.restdemo.producer.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author lindzhao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RpcService {
    Class<?> interfaceType() default Object.class;
    String version() default "1.0";
}
