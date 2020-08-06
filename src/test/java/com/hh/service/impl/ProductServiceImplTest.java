package com.hh.service.impl;

import com.hh.dataobject.ProductInfo;
import com.hh.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    /**
     *new PageRequest()已经过时
     */
    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        //设置商品Id
        productInfo.setProductId("123458");
        productInfo.setProductName("芒果汁");
        productInfo.setProductPrice(new BigDecimal(5.8));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("酸酸甜甜");
        productInfo.setProductIcon("http://maoguo.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        //注意：这个商品类目编号一定要在数据库中存在才可以
        productInfo.setCategoryType(3);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    void onSale(){
        ProductInfo productInfo = productService.onSale("123457");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }

    @Test
    void offSale(){
        ProductInfo productInfo = productService.offSale("123457");
        Assert.assertEquals(ProductStatusEnum.DOWN,productInfo.getProductStatusEnum());
    }
}