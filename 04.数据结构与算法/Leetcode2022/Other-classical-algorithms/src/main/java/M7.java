/**
 * @author cat
 * @description
 * @date 2022/6/15 上午11:52
 */
public class M7 {
    public static void main(String[] args) {
        System.out.println(new Solution7().reverse(392));
        //System.out.println(Integer.MIN_VALUE);
        //System.out.println(Integer.MAX_VALUE);
    }
}

class Solution7 {
    public int reverse(int x) {

        try {
            System.out.println("try-----------");
            return 0;
        } catch (Exception e) {
            System.out.println("catch---------");
        } finally {
            System.out.println("finally--------");
        }


        int rev = 0;
        while(x != 0){
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10){
                return 0;
            }

            // 弹出 x 的末尾数字 digit
            int digit = x % 10;
            x /= 10;

            // 将数字 digit 推入 rev 末尾
            rev = rev * 10 + digit;
        }
        return rev;
    }
}