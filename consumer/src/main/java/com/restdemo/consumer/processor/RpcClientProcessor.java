package com.restdemo.consumer.processor;

import com.example.common.discovery.DiscoveryService;
import com.restdemo.consumer.annotation.RpcAutowired;
import com.restdemo.consumer.proxy.ClientStubProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author lindzhao
 */
public class RpcClientProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private DiscoveryService discoveryService;
    private ClientStubProxyFactory clientStubProxyFactory;

    public RpcClientProcessor(DiscoveryService discoveryService, ClientStubProxyFactory clientStubProxyFactory) {
        this.discoveryService = discoveryService;
        this.clientStubProxyFactory = clientStubProxyFactory;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String definitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(definitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.getClass().getClassLoader());
                ReflectionUtils.doWithFields(clazz, field -> {
                    RpcAutowired rpcAutowired = AnnotationUtils.getAnnotation(field, RpcAutowired.class);
                    if (rpcAutowired != null) {
                        Object bean = this.applicationContext.getBean(clazz);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, bean, clientStubProxyFactory.getProxy(field.getType(), discoveryService));
                        //todo
                    }
                });
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
