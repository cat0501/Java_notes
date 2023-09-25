package com.exam;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author cat
 * @description
 * @date 2023/9/18 20:04
 */
import java.util.*;

    class Node {
        int x;
        int y;
        int steps;

        public Node(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }
    }

    public class A0918_zy_02 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            scanner.nextLine();
            char[][] maze = new char[n][m];
            for (int i = 0; i < n; i++) {
                maze[i] = scanner.nextLine().toCharArray();
            }
            int result = solveMaze(maze, n, m);
            System.out.println(result);
        }

        public static int solveMaze(char[][] maze, int n, int m) {
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上下左右四个方向
            Queue<Node> queue = new LinkedList<>();
            boolean[][] visited = new boolean[n][m];

            // 找到机器人的起点坐标
            int startX = -1, startY = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (maze[i][j] == 's') {
                        startX = i;
                        startY = j;
                        break;
                    }
                }
            }

            queue.offer(new Node(startX, startY, 0));
            visited[startX][startY] = true;

            while (!queue.isEmpty()) {
                Node currentNode = queue.poll();
                int x = currentNode.x;
                int y = currentNode.y;
                int steps = currentNode.steps;

                if (maze[x][y] == 'e') { // 到达终点
                    return steps;
                }

                for (int[] direction : directions) {
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    if (newX >= 0 && newX < n && newY >= 0 && newY < m
                            && maze[newX][newY] != '#' && !visited[newX][newY]) {
                        queue.offer(new Node(newX, newY, steps + 1));
                        visited[newX][newY] = true;
                    }
                }
            }

            return -1; // 无法到达终点
        }
    }
