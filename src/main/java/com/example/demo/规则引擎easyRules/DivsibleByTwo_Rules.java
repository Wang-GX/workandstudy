package com.example.demo.规则引擎easyRules;

import org.jeasy.rules.annotation.*;

@Rule(name = "被2整除", description = "number如果被3整除，打印：number is two")
public class DivsibleByTwo_Rules {

    @Condition //条件判断注解：如果return true， 执行Action
    public boolean isTwoJudge(@Fact("number") int number) {
        return number % 2 == 0;
    }

    @Action //执行方法注解
    public void isTwoAction(@Fact("number") int number) {
        System.out.print("输出结果：[" + number + "] is two !");
    }

    @Priority //优先级注解：return 数值越小，优先级越高
    public int getPriority() {
        return 1;
    }
}
