package com.misnearzhang.config.autoconfigure;


import io.grpc.ClientInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalClientInterceptorRegistry implements ApplicationContextAware {

    public List<ClientInterceptor> getClientInterceptors() {
        return this.clientInterceptors;
    }

    private final List<ClientInterceptor> clientInterceptors = new ArrayList();
    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @PostConstruct
    public void init() {

        Map<String, GlobalClientInterceptorConfigurerAdapter> map = this.applicationContext.getBeansOfType(GlobalClientInterceptorConfigurerAdapter.class);

        for (GlobalClientInterceptorConfigurerAdapter globalClientInterceptorConfigurerAdapter : map.values()) {
            globalClientInterceptorConfigurerAdapter.addClientInterceptors(this);
        }
    }

    public GlobalClientInterceptorRegistry addClientInterceptors(ClientInterceptor interceptor) {
        this.clientInterceptors.add(interceptor);
        return this;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
