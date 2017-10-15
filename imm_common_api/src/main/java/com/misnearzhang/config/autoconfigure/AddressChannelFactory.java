package com.misnearzhang.config.autoconfigure;

import com.google.common.collect.Lists;
import com.misnearzhang.config.properties.GRpcChannelProperties;
import com.misnearzhang.config.properties.GRpcChannelsProperties;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AddressChannelFactory implements GRpcChannelFactory {
    private static final Logger log = LoggerFactory.getLogger(AddressChannelFactory.class);
    private final GRpcChannelsProperties properties;
    private final GlobalClientInterceptorRegistry globalClientInterceptorRegistry;

    public AddressChannelFactory(GRpcChannelsProperties properties, GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
        this.properties = properties;
        this.globalClientInterceptorRegistry = globalClientInterceptorRegistry;
    }

    public ManagedChannel createChannel(String name) {
        return createChannel(name, null);
    }

    public ManagedChannel createChannel(String name, List<ClientInterceptor> interceptors) {
        GRpcChannelProperties channelProperties = this.properties.getChannel(name);
        String host = channelProperties.getServerHost();
        host = "".equals(host) ? name : host;
        Integer port = channelProperties.getServerPort();
        Boolean isEnableKeepAlive = Boolean.valueOf(channelProperties.isEnableKeepAlive());
        Long keyAliveDelay = Long.valueOf(channelProperties.getKeepAliveDelay());

        ManagedChannel channel = NettyChannelBuilder.forAddress(host, port.intValue()).usePlaintext(channelProperties.isPlaintext()).enableKeepAlive(isEnableKeepAlive.booleanValue(), keyAliveDelay.longValue(), TimeUnit.SECONDS, channelProperties.getKeepAliveTimeout(), TimeUnit.SECONDS).build();
        if ((null != channel) && (!channel.isTerminated()) && (!channel.isShutdown())) {
            log.info("gRPC channel - connect to server host: {}, port: {}", host, port);
            log.info("gRPC channel - keep alive : {}, timeout: {} seconds", isEnableKeepAlive.booleanValue() ? "yes" : "no", keyAliveDelay);
        }
        List<ClientInterceptor> globalInterceptorList = this.globalClientInterceptorRegistry.getClientInterceptors();
        Set<ClientInterceptor> interceptorSet = new HashSet();
        if ((globalInterceptorList != null) && (!globalInterceptorList.isEmpty())) {
            interceptorSet.addAll(globalInterceptorList);
        }
        if ((interceptors != null) && (!interceptors.isEmpty())) {
            interceptorSet.addAll(interceptors);
        }
        return (ManagedChannel) ClientInterceptors.intercept(channel, Lists.newArrayList(interceptorSet));
    }
}
