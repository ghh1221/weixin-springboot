package com.hh.service.impl;

import com.hh.dataobject.OrderDetail;
import com.hh.dto.OrderDTO;
import com.hh.enums.OrderStatusEnum;
import com.hh.enums.PayStatusEnum;
import com.hh.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "110110";
    private final String ORDER_ID = "1583721198784404461";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("李四");
        orderDTO.setBuyerAddress("南京");
        orderDTO.setBuyerPhone("137659864");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        //数据库中必须存在
        o1.setProductId("123458");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        //数据库中必须存在
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("创建订单 result={}",result);
    }

    @Test
    void findOnes() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("查询单个订单 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        for( OrderDTO ordeDTO :orderDTOPage){
            System.out.println(ordeDTO);
        }
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    /**
     * 卖家端查询订单列表
     */
    @Test
    void list(){
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        for( OrderDTO ordeDTO :orderDTOPage){
            System.out.println(ordeDTO);
        }
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }
}