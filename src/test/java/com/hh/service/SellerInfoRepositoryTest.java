package com.hh.service;

import com.hh.dataobject.SellerInfo;
import com.hh.repository.SellerInfoRepository;
import com.hh.util.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository repository;

    @Test
    void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        repository.save(sellerInfo);
    }

    @Test
    void findByOpenid() {
        SellerInfo result = repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }

}