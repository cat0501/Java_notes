package com.exam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class GameRanking {
    private Map<String, Integer> scores; // 玩家分数的哈希表
    private TreeMap<Integer, Integer> ranking; // 分数和排名的有序映射

    public GameRanking() {
        scores = new HashMap<>();
        ranking = new TreeMap<>();
    }

    // 加人操作
    public void addPlayer(String role, int score) {
        scores.put(role, score);
        updateRanking(score, 1);
    }

    // 踢人操作
    public void removePlayer(String role) {
        int score = scores.get(role);
        scores.remove(role);
        updateRanking(score, -1);
    }

    // 更新积分操作
    public void updateScore(String role, int delta) {
        int score = scores.get(role);
        int newScore = score + delta;
        scores.put(role, newScore);
        updateRanking(score, -1);
        updateRanking(newScore, 1);
    }

    // 查询排名操作
    public int getRanking(String role) {
        int score = scores.get(role);
        return ranking.lowerEntry(score).getValue() + 1;
    }

    // 更新排行榜
    private void updateRanking(int score, int delta) {
        int count = ranking.getOrDefault(score, 0) + delta;
        if (count == 0) {
            ranking.remove(score);
        } else {
            ranking.put(score, count);
        }
    }
}

public class A44 {
    public static void main(String[] args) {
        // 输入处理
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine(); // 读取换行符
        GameRanking gameRanking = new GameRanking();
        for (int i = 0; i < N; i++) {
            String[] operation = scanner.nextLine().split(" ");
            String command = operation[0];
            String role = operation[1];
            if (command.equals("ADD")) {
                int score = Integer.parseInt(operation[2]);
                gameRanking.addPlayer(role, score);
            } else if (command.equals("DELETE")) {
                gameRanking.removePlayer(role);
            } else if (command.equals("UPDATE")) {
                int delta = Integer.parseInt(operation[2]);
                gameRanking.updateScore(role, delta);
            } else if (command.equals("SEARCH")) {
                int rank = gameRanking.getRanking(role);
                System.out.println(rank);
            }
        }
    }
}
