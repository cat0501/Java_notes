package basic.design_pattern.abstract_factory.phone;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:30
 */
public class PhoneHuawei implements Phone
{
    @Override
    public String call() {
        return "call somebody by huawei phone";
    }
}
