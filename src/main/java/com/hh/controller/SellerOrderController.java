package com.hh.controller;

import com.hh.dto.OrderDTO;
import com.hh.enums.ResultEnum;
import com.hh.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     * @param page 第几页，从第一页开始
     * @param size 一页有多少条数据
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){
        //定义的接口从第一页开始，但是方法时从第0页开始的
        PageRequest request = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        //通过模板引擎渲染
        return new ModelAndView("order/list",map);
//        return new ModelAndView("common/nav");
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancle(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (Exception e) {
            log.error("【卖家端取消订单】查询不到订单", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
       return new ModelAndView("common/success",map);
    }

    /**
     * 订单详情类列表
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (Exception e) {
            log.error("【卖家端取消订单】查询不到订单", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    /**
     * 订单完结
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (Exception e) {
            log.error("【卖家端完结订单】查询不到订单", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
