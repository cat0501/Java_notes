package com.bs.basic.design_pattern.abstract_factory;

import com.bs.basic.design_pattern.abstract_factory.computer.Computer;
import com.bs.basic.design_pattern.abstract_factory.computer.ComputerApple;
import com.bs.basic.design_pattern.abstract_factory.computer.ComputerHuawei;
import com.bs.basic.design_pattern.abstract_factory.phone.Phone;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:39
 */
public class ComputerFactory extends AbstractFactory {
    @Override
    public Phone createPhone(String brand) {
        return null;
    }

    @Override
    public Computer createComputer(String brand) {
        if ("HuaWei".equals(brand)) {
            return new ComputerHuawei();
        } else if ("Apple".equals(brand)) {
            return new ComputerApple();
        } else {
            return null;
        }
    }
}
