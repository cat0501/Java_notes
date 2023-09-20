package com.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class A0917_03 {
    private static int maxScore;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            colors[i] = scanner.nextInt();
        }

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            graph.get(x).add(y);
            graph.get(y).add(x);
        }

        maxScore = 0;
        dfs(graph, colors, k, 0, -1);

        System.out.println(maxScore);
    }

    private static void dfs(List<List<Integer>> graph, int[] colors, int k, int node, int parent) {
        int currentColor = colors[node];
        int flippedColor = 1 - currentColor;

        int score = 0;
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                int neighborColor = colors[neighbor];
                if (neighborColor == currentColor) {
                    if (k > 0) {
                        score++;
                        k--;
                    }
                } else {
                    score++;
                }

                dfs(graph, colors, k, neighbor, node);

                if (k > 0 && colors[neighbor] == flippedColor) {
                    score++;
                    k--;
                }
            }
        }

        maxScore = Math.max(maxScore, score);
    }
}
