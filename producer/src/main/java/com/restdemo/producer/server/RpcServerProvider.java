package com.restdemo.producer.server;

import com.restdemo.producer.register.RegisterService;
import com.restdemo.producer.annotation.RpcService;
import com.restdemo.producer.store.LocalServiceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author lindzhao
 */
@Component
public class RpcServerProvider implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(RpcServerProvider.class);

    @Autowired
    private RegisterService registerService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            try {
                String serviceName = rpcService.interfaceType().getName();
                registerService.register(serviceName);
                LocalServiceCache.store(serviceName, bean);
                logger.info("register service: {}", serviceName);
            } catch (Exception e) {
                logger.error("register or cache service failed.", e);
            }
        }
        return bean;
    }
}
