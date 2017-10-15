package com.misnearzhang.grpc.provider;

import com.google.gson.Gson;
import com.misnearzhang.pojo.User;
import com.misnearzhang.protoc.BaseMessage;
import com.misnearzhang.protoc.RpcServiceGrpc;
import com.misnearzhang.service.UserService;
import com.misnearzhang.config.annotation.GRpcService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Hello world!
 *
 */
@GRpcService
public class OrderServicePrv extends RpcServiceGrpc.RpcServiceImplBase {
    @Autowired
    UserService userService;

    private final Gson gson = new Gson();
    @Override
    public void getUserDate(BaseMessage.Request request, StreamObserver<BaseMessage.Response> responseObserver) {
        List<User> userList =  userService.getUserInfo();
        BaseMessage.Response response = BaseMessage.Response.newBuilder().setId(1000).setStatus(BaseMessage.status.OK).setData(gson.toJson(userList)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void makeOrder(BaseMessage.Request request, StreamObserver<BaseMessage.Response> responseObserver) {
        System.out.println(request.getData());
        BaseMessage.Response response = BaseMessage.Response.newBuilder().setId(2000).setStatus(BaseMessage.status.ERROR).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



