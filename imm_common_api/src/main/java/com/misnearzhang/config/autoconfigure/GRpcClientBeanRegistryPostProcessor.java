package com.misnearzhang.config.autoconfigure;


import io.grpc.ManagedChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.Map;
import java.util.Map.Entry;

public class GRpcClientBeanRegistryPostProcessor
        implements BeanDefinitionRegistryPostProcessor {
    private Map<String, ManagedChannel> serviceBeans;

    public GRpcClientBeanRegistryPostProcessor(Map<String, ManagedChannel> serviceBeans) {
        this.serviceBeans = serviceBeans;
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {
        for (Entry<String, ManagedChannel> entry : this.serviceBeans.entrySet()) {
            RootBeanDefinition serviceDefinition = new RootBeanDefinition(ManagedChannel.class);

            serviceDefinition.setRole(0);
            serviceDefinition.setSource(entry.getValue());
            registry.registerBeanDefinition((String) entry.getKey(), serviceDefinition);
        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
    }
}
