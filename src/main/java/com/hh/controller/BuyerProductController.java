package com.hh.controller;

import com.hh.VO.ProductInfoVO;
import com.hh.VO.ProductVO;
import com.hh.VO.ResultVO;
import com.hh.dataobject.ProductCategory;
import com.hh.dataobject.ProductInfo;
import com.hh.service.CategoryService;
import com.hh.service.ProductService;
import com.hh.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hh
 *
 * 买家商品Controller层的实现，展示善品列表
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVO list(){

        //1.数据库的查询：查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.数据库的查询：查询类目(编号)
        List<Integer> categoryTypeList = new ArrayList<>();
        for(ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList
                = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装：将查询的数据拼装进各个对象中
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //将productInfo对象的属性值拷贝到productInfoVO对象的属性中
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
