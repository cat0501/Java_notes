package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/10/9 20:58
 */
import java.util.ArrayList;
import java.util.HashMap;

public class LongestSubstring {

    public static int findLongestSubstring(String s) {
        // 初始化字符频数的哈希表
        HashMap<Character, Integer> map = new HashMap<>();

        int maxLength = 0; // 最长子串的长度
        int start = 0; // 子串的起始位置

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1); // 更新字符频数

            // 若哈希表中的字符种类大于2，则需要缩小窗口
            while (map.size() > 2) {
                char leftChar = s.charAt(start);
                map.put(leftChar, map.get(leftChar) - 1);
                if (map.get(leftChar) == 0) {
                    map.remove(leftChar);
                }
                start++;
            }

            // 更新最长子串的长度
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add("1");
        list.add("1222");
        list.add("1");

            System.out.println(list.get(list.size()-1));
            System.out.println(list.get(list.size()-2));


        String s = "zhihu";
        int length = findLongestSubstring(s);
        System.out.println("最长的子串长度为: " + length);
    }
}
