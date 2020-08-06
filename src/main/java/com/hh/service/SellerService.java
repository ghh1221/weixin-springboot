package com.hh.service;

import com.hh.dataobject.SellerInfo;

public interface SellerService {
    /**
     * 查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
