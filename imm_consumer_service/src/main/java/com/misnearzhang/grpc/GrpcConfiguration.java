package com.misnearzhang.grpc;

import com.misnearzhang.protoc.RpcServiceGrpc;
import com.misnearzhang.config.annotation.GRpcClient;
import io.grpc.ManagedChannel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
public class GrpcConfiguration {
    @GRpcClient("grpc-server")
    private  ManagedChannel channel;

    @Bean
    public RpcServiceGrpc.RpcServiceBlockingStub blockingStub() {
        return RpcServiceGrpc.newBlockingStub(channel);
    }

}
