package com.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class A0921_03 {
    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();

        List<Integer> sublist1 = new ArrayList<>();
        sublist1.add(3);
        sublist1.add(1);
        sublist1.add(2);
        list.add(sublist1);

        List<Integer> sublist2 = new ArrayList<>();
        sublist2.add(2);
        sublist2.add(5);
        sublist2.add(4);
        list.add(sublist2);

        List<Integer> sublist3 = new ArrayList<>();
        sublist3.add(2);
        sublist3.add(5);
        sublist3.add(2);
        list.add(sublist3);

        List<Integer> sublist4 = new ArrayList<>();
        sublist4.add(2);
        sublist4.add(2);
        sublist4.add(2);
        list.add(sublist4);


        // ... 添加更多子列表

        System.out.println("排序前：" + list);

        Collections.sort(list, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> sublist1, List<Integer> sublist2) {
                int size = Math.min(sublist1.size(), sublist2.size());

                for (int i = 0; i < size; i++) {
                    int num1 = sublist1.get(i);
                    int num2 = sublist2.get(i);

                    if (num1 != num2) {
                        return Integer.compare(num1, num2);
                    }
                }

                return Integer.compare(sublist1.size(), sublist2.size());
            }
        });

        System.out.println("排序后：" + list);
    }
}
