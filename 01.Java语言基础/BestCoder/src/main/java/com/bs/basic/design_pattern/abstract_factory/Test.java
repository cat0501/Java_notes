package com.bs.basic.design_pattern.abstract_factory;

import com.bs.basic.design_pattern.abstract_factory.computer.Computer;
import com.bs.basic.design_pattern.abstract_factory.phone.Phone;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:42
 */
public class Test {
    // 使用工厂类
    // 在需要生产产品时，首先需要定义一个抽象的工厂类AbstractFactory，
    // 然后使用抽象的工厂类生产不同的工厂类，最终根据不同的工厂生产不同的产品。
    public static void main(String[] args) {

        // 工厂类1
        AbstractFactory phoneFactory = new PhoneFactory();
        Phone apple = phoneFactory.createPhone("Apple");
        Phone huaWei = phoneFactory.createPhone("HuaWei");
        System.out.println(apple.call());
        System.out.println(huaWei.call());

        // 工厂类2
        AbstractFactory computerFactory = new ComputerFactory();
        Computer computerApple = computerFactory.createComputer("Apple");
        Computer computerHuaWei = computerFactory.createComputer("HuaWei");
        System.out.println(computerApple.internet());
        System.out.println(computerHuaWei.internet());
        }
}
