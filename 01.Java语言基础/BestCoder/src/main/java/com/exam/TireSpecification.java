package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/10/9 19:45
 */
import java.util.ArrayList;
import java.util.List;

public class TireSpecification {
    public static void main(String[] args) {
        System.out.println(7>>1);


        int[] tw = {225, 235, 245, 255};
        int[] ta = {40, 45, 50, 55, 60};
        int[] tr = {15, 16, 17, 18, 19};
        String query = "轮胎225/55r17";

        List<String> specifications = findSpecifications(tw, ta, tr, query);

        if (specifications.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println(specifications);
        }
    }

    public static List<String> findSpecifications(int[] tw, int[] ta, int[] tr, String query) {
        List<String> specifications = new ArrayList<>();

        String[] parts = query.split("[^0-9a-zA-Z]");
        if (parts.length < 3) {
            return specifications;
        }

        String twStr = parts[parts.length - 3];
        String taStr = parts[parts.length - 2];
        String trStr = parts[parts.length - 1].toUpperCase();

        for (int i = 0; i < tw.length; i++) {
            for (int j = 0; j < ta.length; j++) {
                for (int k = 0; k < tr.length; k++) {
                    if (twStr.equals(String.valueOf(tw[i])) &&
                            taStr.equals(String.valueOf(ta[j])) &&
                            trStr.equals("R" + tr[k])) {
                        specifications.add(tw[i] + "/" + ta[j] + "R" + tr[k]);
                    }
                }
            }
        }

        return specifications;
    }
}
