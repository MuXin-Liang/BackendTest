package com.backend.test.controller;


import com.alibaba.fastjson.JSONObject;
import com.backend.test.common.ResponseEnum;
import com.backend.test.common.ServerResponse;
import com.backend.test.dto.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class HelloController {
    @PostMapping(value = "/hello")
    public ServerResponse hello(@RequestBody TestDTO testDTO){
        JSONObject object = new JSONObject();
        object.put("hashcode","bla bla");
        log.info("TestDTO:\t"+testDTO.getToken());
        return ServerResponse.getInstance().responseEnum(ResponseEnum.SUCCESS).ok().data(object);
    }



}