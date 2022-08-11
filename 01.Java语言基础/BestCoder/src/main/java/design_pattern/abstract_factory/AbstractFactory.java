package design_pattern.abstract_factory;

import design_pattern.abstract_factory.computer.Computer;
import design_pattern.abstract_factory.phone.Phone;

// 抽象工厂定义如下
//用户在需要手机时调用其createPhone()构造一个手机（华为或者苹 果品牌）即可，
// 用户在需要电脑时调用其createComputer()构造一个电脑（华为或者苹果品牌）即可。
public abstract class AbstractFactory {

    public abstract Phone createPhone(String brand);
    public abstract Computer createComputer(String brand);
}
