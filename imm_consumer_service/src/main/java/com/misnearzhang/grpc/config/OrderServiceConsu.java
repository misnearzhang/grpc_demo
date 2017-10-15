package com.misnearzhang.grpc.config;

import com.misnearzhang.protoc.BaseMessage;
import com.misnearzhang.protoc.RpcServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */

@Service
public class OrderServiceConsu {
    @Autowired
    private RpcServiceGrpc.RpcServiceBlockingStub blockingStub;

        public  String getUserInfo(String name){
            BaseMessage.Request request = BaseMessage.Request .newBuilder().setData(name).build();
            BaseMessage.Response response = blockingStub.getUserDate(request);
            return response.getStatus()== BaseMessage.status.OK?response.getData():"err";

        }

        public String makeOrder(){
            BaseMessage.Request request =  BaseMessage.Request.newBuilder().setId(1).setTime(System.currentTimeMillis()).setData("zhanglong grpc request").build();
            BaseMessage.Response response = blockingStub.makeOrder(request);
            return "successful!";
        }
    }
