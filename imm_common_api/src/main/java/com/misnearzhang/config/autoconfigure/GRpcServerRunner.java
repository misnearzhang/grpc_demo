package com.misnearzhang.config.autoconfigure;


import com.misnearzhang.config.annotation.GRpcGlobalInterceptor;
import com.misnearzhang.config.annotation.GRpcService;
import com.misnearzhang.config.properties.GRpcServerProperties;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GRpcServerRunner implements CommandLineRunner, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(GRpcServerRunner.class);
    @Autowired
    private AbstractApplicationContext applicationContext;
    @Autowired
    private GRpcServerProperties gRpcServerProperties;
    private Server server;

    public GRpcServerRunner() {
    }

    public void run(String... args) throws Exception {
        log.info("Starting gRPC Server ...");
        Collection<ServerInterceptor> globalInterceptors = (Collection) this.getBeanNamesByTypeWithAnnotation(GRpcGlobalInterceptor.class, ServerInterceptor.class).map((name) -> {
            return (ServerInterceptor) this.applicationContext.getBeanFactory().getBean(name, ServerInterceptor.class);
        }).collect(Collectors.toList());
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(this.gRpcServerProperties.getPort());
        this.getBeanNamesByTypeWithAnnotation(GRpcService.class, BindableService.class).forEach((name) -> {
            BindableService srv = (BindableService) this.applicationContext.getBeanFactory().getBean(name, BindableService.class);
            ServerServiceDefinition serviceDefinition = srv.bindService();
            GRpcService gRpcServiceAnn = (GRpcService) this.applicationContext.findAnnotationOnBean(name, GRpcService.class);
            serviceDefinition = this.bindInterceptors(serviceDefinition, gRpcServiceAnn, globalInterceptors);
            serverBuilder.addService(serviceDefinition);
            log.info("'{}' service has been registered.", srv.getClass().getName());
        });
        this.server = serverBuilder.build().start();
        log.info("gRPC Server started, listening on port {}.", Integer.valueOf(this.gRpcServerProperties.getPort()));
        this.startDaemonAwaitThread();
    }

    private ServerServiceDefinition bindInterceptors(ServerServiceDefinition serviceDefinition, GRpcService gRpcService, Collection<ServerInterceptor> globalInterceptors) {
        Stream<? extends ServerInterceptor> privateInterceptors = Stream.of(gRpcService.interceptors()).map((interceptorClass) -> {
            try {
                return 0 < this.applicationContext.getBeanNamesForType(interceptorClass).length ? (ServerInterceptor) this.applicationContext.getBean(interceptorClass) : (ServerInterceptor) interceptorClass.newInstance();
            } catch (Exception var3) {
                throw new BeanCreationException("Failed to create interceptor instance.", var3);
            }
        });
        List<ServerInterceptor> interceptors = (List) Stream.concat(gRpcService.applyGlobalInterceptors() ? globalInterceptors.stream() : Stream.empty(), privateInterceptors).distinct().collect(Collectors.toList());
        return ServerInterceptors.intercept(serviceDefinition, interceptors);
    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread() {
            public void run() {
                try {
                    GRpcServerRunner.this.server.awaitTermination();
                } catch (InterruptedException var2) {
                    GRpcServerRunner.log.error("gRPC server stopped.", var2);
                }

            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    public void destroy() throws Exception {
        log.info("Shutting down gRPC server ...");
        Optional.ofNullable(this.server).ifPresent(Server::shutdown);
        log.info("gRPC server stopped.");
    }

    private <T> Stream<String> getBeanNamesByTypeWithAnnotation(Class<? extends Annotation> annotationType, Class<T> beanType) throws Exception {
        return Stream.of(this.applicationContext.getBeanNamesForType(beanType)).filter((name) -> {
            BeanDefinition beanDefinition = this.applicationContext.getBeanFactory().getBeanDefinition(name);
            if (beanDefinition.getSource() instanceof StandardMethodMetadata) {
                StandardMethodMetadata metadata = (StandardMethodMetadata) beanDefinition.getSource();
                return metadata.isAnnotated(annotationType.getName());
            } else {
                return null != this.applicationContext.getBeanFactory().findAnnotationOnBean(name, annotationType);
            }
        });
    }
}
