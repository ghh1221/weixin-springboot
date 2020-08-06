package com.hh.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品包含类目
 */
@Data
public class ProductVO implements Serializable {

    //因为前端使用的是name,为了增加程序的可读性，使用categoryName
    @JsonProperty("name")
    private String categoryName;

    //类目标号
    @JsonProperty("type")
    private Integer categoryType;

    //商品
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
