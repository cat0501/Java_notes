package design_pattern.abstract_factory.phone;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:29
 */
public class PhoneApple implements Phone {
    @Override
    public String call() {
        return "call somebody by apple phone";
    }
}
