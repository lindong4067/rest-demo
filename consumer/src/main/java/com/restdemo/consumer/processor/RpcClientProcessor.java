package com.restdemo.consumer.processor;

import com.restdemo.consumer.discovery.DiscoveryService;
import com.restdemo.consumer.annotation.RpcAutowired;
import com.restdemo.consumer.discovery.NacosDiscoveryServiceImpl;
import com.restdemo.consumer.proxy.ClientStubProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author lindzhao
 */
@Component
public class RpcClientProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProcessor.class);

    private ApplicationContext applicationContext;
    @Autowired
    private DiscoveryService discoveryService;
    @Autowired
    private ClientStubProxyFactory clientStubProxyFactory;

    public RpcClientProcessor() {
    }

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
                        if (clientStubProxyFactory == null) {
                            clientStubProxyFactory = applicationContext.getBean(ClientStubProxyFactory.class);
                        }
                        if (discoveryService == null) {
                            discoveryService = applicationContext.getBean(DiscoveryService.class);
                        }
                        ReflectionUtils.setField(field, bean, clientStubProxyFactory.getProxy(field.getType(), discoveryService));
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
