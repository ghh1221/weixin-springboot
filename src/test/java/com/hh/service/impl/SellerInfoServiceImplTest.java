package com.hh.service.impl;

import com.hh.dataobject.SellerInfo;
import com.hh.service.SellerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoServiceImplTest {
    private static final String openid="abc";

    @Autowired
    private SellerService sellerService;

    @Test
    void findSellerInfoByOpenid() {
        SellerInfo result = sellerService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid,result.getOpenid());
    }
}