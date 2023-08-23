package com.bs.algorithm.hot100;

import java.util.*;

/**
 * @author cat
 * @description
 * @date 2023/7/29 08:29
 */
public class test {
    public static void main(String[] args) {
        //int n = 5; int m = 3;
        //int n = 9; int m = 13;
        //int n = 10; int m = 17;
        int n = 88; int m = 10;
        int lastRemaining = lastRemaining(n, m);
        System.out.println(lastRemaining);
    }

    // 0,1,2,3,4       2\0\4\1
    public static int lastRemaining(int n, int m) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        List<Integer> li;
        li = list;
        while (li.size() != 1){
            li = tmp(li, m);
        }
        return li.get(0);
    }

    public static List<Integer> tmp(List<Integer> list, int m){
        int size = list.size();
        System.out.println("当前数组长度size为：" + size);

        int trim;
        System.out.println("去除前集合为：" + list);
        if (m > size){
            System.out.println("当前集合长度小于 m");
            trim = m % size;
            if (trim == 0){
                trim = size;
                list.remove( size - 1);
            } else {
                list.remove(trim - 1);
            }

        } else if(m == size){
            System.out.println("当前集合长度等于 m");
            trim = m;
            list.remove(trim - 1);
        } else {
            System.out.println("当前集合长度大于 m");
            trim = m;
            list.remove((trim - 1));
        }
        System.out.println("去除元素位置：" + trim);
        System.out.println("去除元素后的集合为：" + list);

        System.out.println("重新组装数据————————————————————》》》");
        List<Integer> firstHalf;
        List<Integer> secondHalf;
        if (list.size() == trim){
            firstHalf = list.subList(0, trim - 1);
            secondHalf = list.subList(trim - 1, list.size());
        } else if (list.size() <= trim){
            firstHalf = list.subList(0, trim - 1);
            secondHalf = list.subList(trim - 1, list.size());
        } else {
            firstHalf = list.subList(0, trim - 1);
            secondHalf = list.subList(trim - 1, list.size());
        }

        System.out.println("First Half: " + firstHalf);
        System.out.println("Second Half: " + secondHalf);

        List<Integer> li = new ArrayList<>();
        if (trim != size){
            // 去除元素位置后还有元素
            li.addAll(secondHalf);
            li.addAll(firstHalf);
        } else {
            li.addAll(firstHalf);
            li.addAll(secondHalf);
        }

        //for (int i = trim; i < size; i++) {
        //    li.add(i);
        //}
        //for (int i = 0; i < trim - 1; i++) {
        //    li.add(i);
        //}
        System.out.println("重组后的数组为：" + li);
        System.out.println("___________________________");
        return li;
    }


    // [2,7,11,15]
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])){
                return new int[]{hashMap.get(target - nums[i]), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[]{};
    }

    public static int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        int[] arr = new int[length];
        // 目标数组 [7, 2, -2, -6]
        for(int i = 0;i < length; i++){
            arr[i] = target - nums[i];
        }
        // 遍历
        int a = 0; int b = 0;
        for(int i = 0; i < length; i++){
            if (a != 0 && b != 0){
                break;
            }
            // nums[0] = 2
            for(int j = 0; j < length; j++){
                if(nums[i] == arr[j]){
                    a = i;
                    b = j;
                    break;
                }
            }
        }
        System.out.println(a);
        System.out.println(b);
        return new int[]{a, b};
    }
}


