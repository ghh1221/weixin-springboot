package com.hh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //公众号appid
    private String mpAppId;

    //公众号appSecret
    private String mpAppSecret;

    //商户号
    private String mchId;

    //商户秘钥
    private String mchKey;
    
    //商户证书路径
    private String keyPath;

    //微信支付异步通知
    private String notifyUrl;

    //开放平台id
    private String openAppId;

    //开放平台秘钥
    private String openAppSecret;

    /**
     * 微信模版id
     */
    private Map<String, String> templateId;
}
