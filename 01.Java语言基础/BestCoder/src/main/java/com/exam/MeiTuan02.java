package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/23 10:01
 */

import java.util.Scanner;

public class MeiTuan02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入初始时间
        String startTimeStr = scanner.nextLine();

        // 输入操作次数
        int n = Integer.parseInt(scanner.nextLine());

        // 分离小时和分钟
        int hours = Integer.parseInt(startTimeStr.substring(0, 2));
        int minutes = Integer.parseInt(startTimeStr.substring(3, 5));

        System.out.println();
        System.out.println();

        // 循环进行操作
        for (int i = 0; i < n; i++) {
            // 输入操作方式和分钟数
            String[] operation = scanner.nextLine().split(" ");
            char ch = operation[0].charAt(0);
            int change = Integer.parseInt(operation[1]);

            // 根据操作方式进行相应调整
            if (ch == '+') {
                minutes += change;
            } else if (ch == '-') {
                minutes -= change;
            }

            // 调整小时和分钟的范围
            if (minutes >= 60) {
                hours = (hours + minutes / 60) % 24;
                minutes %= 60;
            } else if (minutes < 0) {
                int borrow = Math.abs(minutes) / 60 + 1;
                hours = (hours - borrow) % 24;
                if (hours < 0) {
                    hours += 24;
                }
                minutes = (minutes + borrow * 60) % 60;
            }
        }

        // 调整小时和分钟的格式
        String newHours = String.format("%02d", hours);
        String newMinutes = String.format("%02d", minutes);

        // 输出最终时间
        System.out.println(newHours + ":" + newMinutes);

        scanner.close();
    }
}
