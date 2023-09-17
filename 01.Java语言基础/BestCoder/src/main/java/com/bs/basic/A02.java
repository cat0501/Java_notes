package com.bs.basic;

import java.util.*;


class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {
      this.val = val;
    }
  }

public class A02 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @return int整型二维数组
     */
    public int[][] levelOrderBottom (TreeNode root) {
        // write code here

        // 一些特殊情况的处理
        if (null == root.left && null == root.right) {
            return new int[][] {{root.val}};
        }

        // 定义一系列的变量
        Queue<TreeNode> queue = new
                LinkedList<>(); // 定义一个队列，用于实现广度优先遍历
        HashMap<TreeNode, Integer> hashMap = new
                HashMap<>(); // 定义一个 HashMap，用于存放每个节点和它所在的层数
        ArrayList<Integer> tmpArrayList = new
                ArrayList<>(); // 定义一个 ArrayList，用于存放同一层的所有节点
        Stack<ArrayList<Integer>> stack = new
                Stack<>(); // 定义一个栈，用于实现逆序打印二叉树
        TreeNode node = root; // 定义一个辅助节点
        int curLevel =
                1; // 定义一个整型变量，告知当前遍历到树的第几层

        // 初始化
        queue.add(node);
        hashMap.put(node, 1);

        // 广度优先遍历(改)
        while (!queue.isEmpty()) {
            // 弹出一个节点
            node = queue.poll();
            int nodeLevel = hashMap.get(node); // 获取当前节点所在的层数
            if (nodeLevel != curLevel) {
                stack.push(tmpArrayList);
                tmpArrayList = new ArrayList<>();
                tmpArrayList.add(node.val);
                curLevel = nodeLevel;
            } else {
                tmpArrayList.add(node.val);
            }

            if (null != node.left) {
                queue.add(node.left);
                hashMap.put(node.left, hashMap.get(node) + 1);
            }
            if (null != node.right) {
                queue.add(node.right);
                hashMap.put(node.right, hashMap.get(node) + 1);
            }
        }
        // 别忘了，tmpArrayList 中可能还有数据
        if (tmpArrayList.size() != 0) {
            stack.push(tmpArrayList);
        }
        int depth = stack.size(); // 定义一个整型变量，用于存放树的深度
        int[][] res = new
                int[depth][]; // 定义一个二维数组，用于存放最终的返回结果
        curLevel = 0;
        while (!stack.isEmpty()) {
            tmpArrayList = stack.pop(); // 弹出一层
            res[curLevel] = new int[tmpArrayList.size()];
            for (int j = 0; j < tmpArrayList.size(); j++) {
                res[curLevel][j] = tmpArrayList.get(j);
            }
            curLevel++;
        }
        return res;
    }
}


