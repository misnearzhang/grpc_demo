package com.misnearzhang.config.autoconfigure;


import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;

import java.util.List;

public abstract interface GRpcChannelFactory {
    public abstract ManagedChannel createChannel(String paramString);

    public abstract ManagedChannel createChannel(String paramString, List<ClientInterceptor> paramList);
}
