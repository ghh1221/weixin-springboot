package com.hh.repository;

import com.hh.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//写一个接口继承接口JpaRepository<ProductCategory,Integer>，第一个参数为实体类，第二个参数为主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
