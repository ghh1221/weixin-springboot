package com.hh.converter;

import com.google.gson.Gson;;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hh.dataobject.OrderDetail;
import com.hh.dto.OrderDTO;
import com.hh.enums.ResultEnum;
import com.hh.exception.SellException;
import com.hh.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //将前端中items属性的json格式转换为List集合
        Gson gson = new Gson();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            log.error("【对象转换】错误,string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
