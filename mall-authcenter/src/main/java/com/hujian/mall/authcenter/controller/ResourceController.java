package com.hujian.mall.authcenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源服务器的测试controller
 * @author hujian
 */
@RequestMapping("/resource")
@RestController
public class ResourceController {
    @GetMapping("/getResourceTest")
    public String getResourceTest(){
        return ("这是resource的测试方法 getResourceTest()");
    }
}
