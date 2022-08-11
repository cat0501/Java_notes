package design_pattern.abstract_factory;

import design_pattern.abstract_factory.computer.Computer;
import design_pattern.abstract_factory.phone.Phone;
import design_pattern.abstract_factory.phone.PhoneHuawei;
import design_pattern.abstract_factory.phone.PhoneApple;

public class PhoneFactory extends AbstractFactory {

    public Phone createPhone(String phoneName) {
        if (phoneName.equals("HuaWei")) {
            return new PhoneHuawei();
        } else if (phoneName.equals("Apple")) {
            return new PhoneApple();
        } else {
            return null;
        }
    }

    @Override
    public Computer createComputer(String brand) {
        return null;
    }
}
