package com.exam;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class wangyiT4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean[] sout = new boolean[n];

        for (int i = 0; i < n; i++) {
            String str = sc.next();
            StringBuilder sb = new StringBuilder(str);
            boolean flag = false;
            for (int j = 0; j <= sb.length(); j++) {
                for (int k = 0; k <= 9; k++) {
                    sb.insert(j, k);
                    String[] split = sb.toString().split("=");
                    if (cal(split[0]) == cal(split[1])) {
                        flag = true;
                        break;
                    }
                    sb.deleteCharAt(j);
                }
                if (flag) {
                    break;
                }
            }

            sout[i] = flag;
        }


        for (boolean b : sout) {
            if (b) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    private static int cal(String s) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        char preSign = '+';
        int num = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == n - 1) {
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    default:
                        stack.push(stack.pop() / num);
                }
                preSign = s.charAt(i);
                num = 0;
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.pop();
        }
        return ans;
    }
}
