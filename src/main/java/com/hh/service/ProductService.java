package com.hh.service;

import com.hh.dataobject.ProductInfo;
import com.hh.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author hh
 * 商品Service层接口
 */
public interface ProductService {
    /**
     * 根据productId查询某个商品
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有的商品
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 添加商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //商品上架
    ProductInfo onSale(String productId);
    //商品下架
    ProductInfo offSale(String productId);

}
