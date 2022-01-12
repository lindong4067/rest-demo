package com.restdemo.producer.server;

import com.example.common.register.RegisterService;
import com.restdemo.producer.annotation.RpcService;
import com.restdemo.producer.store.LocalServiceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author lindzhao
 */
public class RpcServerProvider implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(RpcServerProvider.class);

    private RegisterService registerService;

    public RpcServerProvider(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService annotation = bean.getClass().getAnnotation(RpcService.class);
        if (annotation != null) {
            try {
                String serviceName = annotation.interfaceType().getName();
                registerService.register(serviceName);
                LocalServiceCache.store(serviceName, bean);
            } catch (Exception e) {
                logger.error("register or cache service failed.", e);
            }
        }
        return bean;
    }
}
