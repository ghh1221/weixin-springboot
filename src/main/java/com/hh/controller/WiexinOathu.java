package com.hh.controller;

import com.hh.util.WeiXinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/weixin")
public class WiexinOathu {

    // 1、当用户请求这个方法时,我们需要引导用户打开url这个地址,请求授权
    @RequestMapping("/oauth")
    public void autho(HttpServletResponse response)  {
        //redirect_uri
        String path = "http://heng.nat300.top/sell/weixin/invoke";
        try {
            URLEncoder.encode(path,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=wxd0430ca3dde79d52" +
            "&redirect_uri="+ path +
            "&response_type=code" +
            "&scope=snsapi_userinfo" +
            "&state=STATE" +
            "#wechat_redirect";

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用户同意授权后，页面将跳转到这个路径：http://heng.nat300.top/sell/weixin/invoke
    @RequestMapping("/invoke")
    public void oauthInvoke(HttpServletRequest request){
        //2、从回调地址中获取code
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        System.out.println("code="+code);

        //3、认证服务器,获取code后，请求以下链接获取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=wxd0430ca3dde79d52" +
                "&secret=46088f58a30ffb26b12d921735d26691" +
                "&code="+code +
                "&grant_type=authorization_code";

        //认证服务器响应的json数据,获取access_token 和 openid
        JSONObject jsonObject = WeiXinUtil.httpRequest(url,"POST",null);
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        System.out.println("access_token="+access_token);
        System.out.println("openid="+openid);

        //4、根据openid和access_token获取资源信息
        String URLUserinfo = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid="+openid+
                "&lang=zh_CN";
        JSONObject jsonobj = WeiXinUtil.httpRequest(URLUserinfo,"GET",null);
        System.out.println(jsonobj);
    }
}
