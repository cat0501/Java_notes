package design_pattern.factory;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:16
 */

// 2 定义实现类
public class Iphone implements Phone{
    @Override
    public String brand() {
        return "this is a apple iphone";
    }
}
