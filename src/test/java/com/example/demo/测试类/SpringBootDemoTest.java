package com.example.demo.测试类;

import com.example.demo.DemoApplication;
import com.example.demo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

/*
        注意点：
        org.junit.jupiter.api.Test：引入@Test注解的测试类和测试方法可以不使用public修饰。(spring-boot-starter-parent-2.2.0.RELEASE----->spring-boot-starter-test-2.2.0.RELEASE----->junit-jupiter)
        org.junit.Test：引入@Test注解的测试类和测试方法必须使用public修饰，否则测试方法无法运行。(spring-boot-starter-parent-2.2.0.RELEASE↓----->spring-boot-starter-test-2.2.0.RELEASE↓----->junit)
        无论使用哪一个版本的SpringBoot，都会引入junit依赖，建议使用org.junit.Test。

        如果要查看当前工程所有依赖之间的关系，点击IDEA的右侧栏Maven Projects中的磁场图标。
        如果要查看类或接口的继承关系以及使用了哪些注解，选中右击Diagrams
            蓝色箭头表示继承，绿色箭头表示实现，黄色虚线表示注解
    */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)//SpringBoot项目，通过引导类启动时加载Spring的IOC容器
public class SpringBootDemoTest {

    @Autowired
    @Qualifier("userByDefaultConfig")
    private User user;

    @Test
    @Ignore("跳过test1")//表示跳过当前测试方法，注意：如果要使用此注解，那么该方法上的@Test注解必须是org.junit.Test而不能是org.junit.jupiter.api.Test
    public void test1(){
        System.out.println(user);
    }

    @Test
    public void test2(){
        System.out.println(user);
    }


}
