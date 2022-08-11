package design_pattern.factory;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:20
 */

// 3 定义工厂类
// 根据不同的参数实例化不同品牌的手机类并返回

// 实现了：工厂类根据不同的参数创建不同的实例，对调用者来说屏蔽了实例化的细节。
public class PhoneFactory {

    public Phone createPhone(String phoneName) {
        if (phoneName.equals("Huawei")) {
            return new Huawei();
        } else if (phoneName.equals("Iphone")) {
            return new Iphone();
        } else {
            return null;
        }
    }
}
