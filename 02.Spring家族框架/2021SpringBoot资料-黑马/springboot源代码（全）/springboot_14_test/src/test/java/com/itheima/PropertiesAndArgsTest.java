package com.itheima;

import com.itheima.testcase.domain.BookCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
//properties属性可以为当前测试用例添加临时的属性配置
//@SpringBootTest(properties = {"test.prop=testValue1"})
//args属性可以为当前测试用例添加临时的命令行参数
//@SpringBootTest(args={"--test.prop=testValue2"})
@SpringBootTest(properties = {"test.prop=testValue1"},args={"--test.prop=testValue2"})
public class PropertiesAndArgsTest {

    @Value("${test.prop}")
    private String msg;

    @Autowired
    private BookCase bookCase;

    @Test
    void testProperties(){
        System.out.println(msg);
        System.out.println(bookCase);
    }

}
