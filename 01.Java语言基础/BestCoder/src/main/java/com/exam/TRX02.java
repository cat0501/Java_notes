package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/24 18:54
 */

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TRX02 {
    public String frequencySort(String s) {
        // Create a HashMap to store character frequencies
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        // Create a PriorityQueue to sort characters based on their frequencies
        PriorityQueue<Character> pq = new PriorityQueue<>((x, y) -> map.get(y) - map.get(x));
        for (char ch : map.keySet()) {
            pq.offer(ch);
        }

        // Build the sorted string
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            char key = pq.poll();
            int count = map.get(key);
            while (count-- > 0) {
                sb.append(key);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        TRX02 frequencySort = new TRX02();
        String output = frequencySort.frequencySort(input);

        System.out.println(output);

        scanner.close();
    }
}
