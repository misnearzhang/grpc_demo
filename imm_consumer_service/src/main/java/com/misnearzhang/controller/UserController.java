package com.misnearzhang.controller;

import com.misnearzhang.grpc.config.OrderServiceConsu;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhanglong on 2017/7/24.
 */
@RestController
public class UserController {

    @Autowired
    OrderServiceConsu orderServiceConsu;

    @ApiOperation(value = "index",notes = "一个测试的东西")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Object get(){
        return orderServiceConsu.getUserInfo("nihk");
    }


    @ApiOperation(value = "makeOrder",notes = "一个测试的东西")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/make-order" , method = RequestMethod.POST)
    public Object makeOrder(){
        return orderServiceConsu.makeOrder();
    }

}
