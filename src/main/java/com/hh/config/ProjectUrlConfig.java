package com.hh.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    //微信公众屏号授权url
    public String wechatMpAuthorize;

    //微信开放平台url
    public String wechatOpenAuthorize;

    //点餐系统
    public String sell;
}
