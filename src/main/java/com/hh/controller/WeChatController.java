package com.hh.controller;

import com.hh.config.ProjectUrlConfig;
import com.hh.enums.ResultEnum;
import com.hh.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //http://heng.nat300.top/sell/wechat/authorize
    @GetMapping("/authorize")
    public String authorize(){
        //returnUrl就是用户授权同意后回调的地址
        String returnUrl="http://heng.nat300.top/sell/wechat/userInfo";
        //引导用户访问这个链接，进行授权
        String url = wxMpService.oauth2buildAuthorizationUrl(returnUrl, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        return "redirect:"+url;
    }

    //用户授权同意后回调的接口，从请求参数中获取code
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("openid={}", openId);

        //这个地址可有可无，反正只是为了拿到openid，但是如果没有会报400错误，为了好看随便返回一个百度的地址
        returnUrl = "http://www.baidu.com";

        return "redirect:" + returnUrl;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize() {
        //returnUrl就是用户授权同意后回调的地址
        String returnUrl = "http://heng.nat300.top/sell/wechat/qrUserInfo";

        //引导用户访问这个链接，进行授权
        String url = wxOpenService.buildQrConnectUrl(returnUrl, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        return "redirect:" + url;
    }

    //用户授权同意后回调的地址，从请求参数中获取code
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            //通过code获取access_token
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        //从token中获取openid
        String openId = wxMpOAuth2AccessToken.getOpenId();

        //这个地址可有可无，反正只是为了拿到openid，但是如果没有会报404错误，为了好看随便返回一个百度的地址
        String  returnUrl = "http://heng.nat300.top/sell/seller/login";

        log.info("openid={}", openId);

        return "redirect:" + returnUrl + "?openid="+openId;
    }
}
