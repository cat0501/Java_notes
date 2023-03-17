package basic.design_pattern.abstract_factory.computer;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:38
 */
public class ComputerHuawei implements Computer{
    @Override
    public String internet() {
        return "华为电脑";
    }
}
