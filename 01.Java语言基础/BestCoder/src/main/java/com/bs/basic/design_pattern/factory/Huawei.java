package com.bs.basic.design_pattern.factory;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午9:17
 */

// 2 定义实现类
public class Huawei implements Phone{

    @Override
    public String brand() {
        return "this is a huawei phone";
    }
}
