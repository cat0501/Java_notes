package com.exam;

import java.util.*;

public class A0917_02 {
    static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] students = new int[n][2];
        for (int i = 0; i < n; i++) {
            students[i][0] = scanner.nextInt();
            students[i][1] = scanner.nextInt();
        }
        int minTime = findMinimumTime(n, students);
        System.out.println(minTime);
    }

    private static int findMinimumTime(int n, int[][] students) {
        int minTime = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int time = bfs(students[i][0], students[i][1]);
            minTime = Math.min(minTime, time);
        }
        return minTime;
    }

    private static int bfs(int startX, int startY) {
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new int[]{startX, startY, 0});
        visited.add(startX + "-" + startY);
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];
            int time = curr[2];
            if (y == 0) { // 表示到达走廊的最底部
                return time + Math.abs(x); // 加上走到出口的时间
            }
            for (int[] direction : directions) {
                int nextX = x + direction[0];
                int nextY = y + direction[1];
                if (nextY >= 0 && nextY <= 4 && Math.abs(nextX) <= nextY && !visited.contains(nextX + "-" + nextY)) {
                    queue.offer(new int[]{nextX, nextY, time + 1});
                    visited.add(nextX + "-" + nextY);
                }
            }
        }
        return -1; // 无法到达出口
    }
}
