package com.hh.service.impl;

import com.hh.dto.OrderDTO;
import com.hh.service.OrderService;
import com.hh.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class PayServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Test
    void create() {
        OrderDTO orderDTO = orderService.findOne("1234578");
        payService.create(orderDTO);
    }

    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("12345896");
        payService.refund(orderDTO);
    }

}