package com.hh;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    //注意这里LoggerFactory包别导错了
    //打印当前类LoggerTest的信息
//    private final Logger logger
//            = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        String name = "heng";
        String password = "123";
        //打印结果
        log.info("name:{},password:{}",name,password);
        log.debug("debug...");
        log.info("info...");
        log.error("error...");
    }
}
