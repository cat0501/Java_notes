/**
 * @author cat
 * @description
 * @date 2022/6/29 下午9:39
 */
// 本题为考试多行输入输出规范示例，无需提交，不计分。
import java.util.Scanner;

//public class Main {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int ans = 0, x;
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                x = sc.nextInt();
//                ans += x;
//            }
//        }
//        System.out.println(ans);
//    }
//}


public class P1 {
    public static void main(String[] args) {
        // 读取两个数字
        //Integer m = 0;
        //Integer n = 0;
        Scanner sum = new Scanner(System.in);

        int m = sum.nextInt();
        int n = sum.nextInt();

        //System.out.println(p);
        //System.out.println(q);
        // 无解的情况：输出-1
        if (n > m){
            System.out.println(-1);
            return;
        }
        // 有解情况：求解
        int tmp = 0;
        int[] arr = new int[n];
        int index = 0;
        boolean result = false;

        for (int i = 1; i <= m; i++) {
            for (int j = i; j <= i + n - 1; j++) {
                tmp += j;
                arr[index++] = j;
            }

            if (tmp == m){
                result = true;
                for (int j = 0; j < n; j++) {
                    System.out.print(arr[j] + " ");
                }
            } else{
                tmp = 0;
                index = 0;
            }
            index = 0;
        }

        if (!result){
            System.out.println(-1);
        }

    }

}
