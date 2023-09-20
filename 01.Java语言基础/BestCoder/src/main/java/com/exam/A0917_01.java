package com.exam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class A0917_01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String categories = scanner.nextLine();
        String books = scanner.nextLine();

        Map<Character, Integer> bookCounts = new HashMap<>();

        // 统计每个类别的书籍数量
        for (char book : books.toCharArray()) {
            if (categories.contains(String.valueOf(book))) {
                bookCounts.put(book, bookCounts.getOrDefault(book, 0) + 1);
            }
        }

        // 输出结果
        for (char category : bookCounts.keySet()) {
            System.out.println(category + " " + bookCounts.get(category));
        }
    }
}
