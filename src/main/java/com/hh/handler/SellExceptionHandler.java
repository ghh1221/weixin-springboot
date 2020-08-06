package com.hh.handler;

import com.hh.VO.ResultVO;
import com.hh.exception.ResponseBankException;
import com.hh.exception.SellException;
import com.hh.exception.SellerAuthorizeException;
import com.hh.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {

//        return new ModelAndView("redirect:"
//                .concat("http://heng.nat300.top")
//                .concat("/sell/wechat/qrAuthorize")
//                .concat("?returnUrl=")
//                .concat("http://heng.nat300.top")
//                .concat("/sell/seller/login"));

        //拦截异常后，跳转到登录界面
        return new ModelAndView("redirect:".concat("https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=wx6ad144e54af67d87" +
                "&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2FoTgZpwenC6lwO2eTDDf_-UYyFtqI" +
                "&response_type=code&scope=snsapi_login" +
                "&state=http%3a%2f%2fheng.nat300.top%2fsell%2fwechat%2fqrUserInfo"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {

    }
}
