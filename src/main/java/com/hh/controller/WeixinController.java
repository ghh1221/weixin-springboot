package com.hh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @RequestMapping("/auth")
    public void auth(@RequestParam("code")String code) {

        log.info("进入auth方法。。。");
        log.info("code={}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxd0430ca3dde79d52&secret=46088f58a30ffb26b12d921735d26691&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        //get指调用http的get方法，object指Http响应转换为我们选择的对象类型
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
}
