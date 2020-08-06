package com.hh.repository;

import com.hh.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {

    @Autowired
    ProductCategoryRepository repository;

    //查询方法
    @Test
    public void getOneTest(){
        ProductCategory productCategory = repository.getOne(1);
        System.out.println(productCategory.toString());
    }

    //添加方法
    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(1);
        repository.save(productCategory);
    }

    //更新方法，需要有主键
    @Test
    public void saveTest1(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    //测试添加方法
    @Test
    //这个注解表明测试的数据不会添加到数据库，不论成功还是失败都会回滚
    @Transactional
    public void saveTest2(){
        //使用带参构造函数
        ProductCategory productCategory =
                new ProductCategory("女生最爱",3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}
