package com.hh.service.impl;

import com.hh.dto.OrderDTO;
import com.hh.enums.ResultEnum;
import com.hh.exception.SellException;
import com.hh.service.BuyerService;
import com.hh.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    public OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO.getOrderId()==null){
            return null;
        }
        //判断是否为自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单的openid不一致,openid={}",openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancleOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO==null){
            log.error("【取消订单】查不到该订单,orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
}
