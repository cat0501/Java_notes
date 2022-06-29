/**
 * @author cat
 * @description
 * @date 2022/6/19 上午10:33
 */
//Return_Finally
public class ExceptionTest1 {

    public static void main(String[] args) {
        System.out.println(A.a());
    }

}

class A {
    public static int a() {
        int i = 1;
        try {
            return i;
        } finally {
            System.out.println("f1==>>" + i);
            ++i;
            System.out.println("f2==>>" + i);
        }

    }

}