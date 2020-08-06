package com.hh.service.impl;

import com.hh.dataobject.SellerInfo;
import com.hh.repository.SellerInfoRepository;
import com.hh.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository repository;

    public SellerInfo findSellerInfoByOpenid(String openid){
        return repository.findByOpenid(openid);
    }
}
