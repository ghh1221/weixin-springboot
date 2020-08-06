package com.hh.service;


public interface SecKillService {

    /**
     * 查询秒杀活动特价商品的信息
     */
    String querySecKillProductInfo(String productId);

    /**
     * 模拟不同用户秒杀同一商品的请求
     */
    void orderProductMockDiffUser(String productId);

}
