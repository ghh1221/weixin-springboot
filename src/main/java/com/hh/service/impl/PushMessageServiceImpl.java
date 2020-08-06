package com.hh.service.impl;


import com.hh.config.WechatAccountConfig;
import com.hh.dto.OrderDTO;
import com.hh.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //模板ID
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        //卖家公众号的openid
        //templateMessage.setToUser(orderDTO.getBuyerOpenid());

        //测试号的openid
        templateMessage.setToUser("oaaZ7txEYH7kpwRVfBCc9hych-CE");

        //推送的消息
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
        templateMessage.setData(data);

        try {
            //推送
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
}
