package com.misnearzhang.config.autoconfigure;


import com.google.common.collect.Lists;
import com.misnearzhang.config.annotation.GRpcClient;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public class GRpcClientBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(GRpcClientBeanPostProcessor.class);

    private Map<String, List<Class>> beansToProcess = new HashMap();
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private GRpcChannelFactory channelFactory;

    @Autowired
    public GRpcClientBeanPostProcessor() {
        log.debug("GRpcClientBeanPostProcessor");
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        do {
            Field[] fields = clazz.getDeclaredFields();
            int var5 = fields.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Field field = fields[var6];
                if (field.isAnnotationPresent(GRpcClient.class)) {
                    if (!this.beansToProcess.containsKey(beanName)) {
                        this.beansToProcess.put(beanName, new ArrayList());
                    }

                    ((List) this.beansToProcess.get(beanName)).add(clazz);
                }
            }

            clazz = clazz.getSuperclass();
        } while (clazz != null);

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.beansToProcess.containsKey(beanName)) {
            Object target = null;
            try {
                target = this.getTargetBean(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Iterator beans = ((List) this.beansToProcess.get(beanName)).iterator();

            while (beans.hasNext()) {
                Class clazz = (Class) beans.next();
                Field[] fields = clazz.getDeclaredFields();
                int var7 = fields.length;

                for (int i = 0; i < var7; ++i) {
                    Field field = fields[i];
                    GRpcClient annotation = (GRpcClient) AnnotationUtils.getAnnotation(field, GRpcClient.class);
                    if (null != annotation) {
                        List<ClientInterceptor> list = Lists.newArrayList();
                        Class[] clazzes = annotation.interceptors();
                        int var13 = clazzes.length;

                        for (int j = 0; j < var13; ++j) {
                            Class<? extends ClientInterceptor> clientInterceptorClass = clazzes[j];
                            ClientInterceptor clientInterceptor;
                            if (this.beanFactory.getBeanNamesForType(ClientInterceptor.class).length > 0) {
                                clientInterceptor = (ClientInterceptor) this.beanFactory.getBean(clientInterceptorClass);
                            } else {
                                try {
                                    clientInterceptor = (ClientInterceptor) clientInterceptorClass.newInstance();
                                } catch (IllegalAccessException | InstantiationException ex) {
                                    throw new BeanCreationException("Failed to create interceptor instance", ex);
                                }
                            }

                            list.add(clientInterceptor);
                        }

                        ManagedChannel channel = this.channelFactory.createChannel(annotation.value(), list);
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, target, channel);
                    }
                }
            }
        }

        return bean;
    }

    private Object getTargetBean(Object bean) throws Exception {
        try {
            Object target;
            for (target = bean; AopUtils.isAopProxy(target); target = ((Advised) target).getTargetSource().getTarget()) {
                ;
            }

            return target;
        } catch (Throwable var3) {
            throw var3;
        }
    }
}

