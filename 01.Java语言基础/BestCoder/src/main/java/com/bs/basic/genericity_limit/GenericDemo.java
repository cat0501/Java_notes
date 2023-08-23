package com.bs.basic.genericity_limit;

import java.util.ArrayList;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午9:00
 */


// https://www.bilibili.com/video/BV1Cv411372m?p=132
// 黑马讲的很不错，理解了泛型的概述和优势、自定义泛型类/泛型方法/泛型接口、泛型通配符以及上下限（4月11日面试考题）
public class GenericDemo {
    public static void main(String[] args) {
        ArrayList<BMW> bmws = new ArrayList<>();
        bmws.add(new BMW());
        bmws.add(new BMW());
        bmws.add(new BMW());
        go(bmws);

        ArrayList<BENZ> benzs = new ArrayList<>();
        benzs.add(new BENZ());
        benzs.add(new BENZ());
        benzs.add(new BENZ());
        go(benzs);

        // 不符合，狗不应该能够比赛
        //ArrayList<com.bs.basic.genericity_limit.Dog> dogs = new ArrayList<>();
        //dogs.add(new com.bs.basic.genericity_limit.Dog());
        //dogs.add(new com.bs.basic.genericity_limit.Dog());
        //dogs.add(new com.bs.basic.genericity_limit.Dog());
        //go(dogs);
    }

    /**
     * 所有车比赛
     * @param cars
     */
    // 虽然BMW和BENZ都继承了Car, 但是ArrayList<com.bs.basic.genericity_limit.BMW>和ArrayList<com.bs.basic.genericity_limit.BENZ>与ArrayList<com.bs.basic.genericity_limit.Car>没有关系的！
    // (1)类有继承关系，但是父子类各自的集合没有关系
    //public static void go(ArrayList<com.bs.basic.genericity_limit.Car> cars){
    // }

    // (2)使用泛型可以，但是狗不应该能够比赛，所以下面我们使用泛型的上下限
            // ? extends com.bs.basic.genericity_limit.Car: ?必须是Car或者其子类   泛型上限
            // ? super com.bs.basic.genericity_limit.Car ： ?必须是Car或者其父类   泛型下限

    // 狗不应该可以比赛
    public static void go2(ArrayList<?> cars){
    }

    public static void go(ArrayList<? extends Car> cars){

    }


}

// -------------------------------------
// 不应该能比赛的狗
class Dog{

}


class BENZ extends Car{

}

class BMW extends Car{

}

class Car{

}
