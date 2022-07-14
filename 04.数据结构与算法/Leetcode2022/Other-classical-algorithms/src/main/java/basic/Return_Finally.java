package basic;

import java.util.HashMap;

/**
 * @author cat
 * @description
 * @date 2022/7/4 上午11:09
 */
public class Return_Finally {

    public static void main(String[] args) {
        new HashMap<String, String>();
        System.out.println(A.a());
    }

}
class A {
    public static int a() {
        int i = 1;
        try{
            // return 先执行，将方法停下后执行 finally
            // 最后再执行 return 将值返回
            return i;
        }finally {
            System.out.println("f1==>>"+i);
            ++i;
            System.out.println("f2==>>"+i);
        }
    }
}
