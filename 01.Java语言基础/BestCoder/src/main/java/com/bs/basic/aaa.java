package com.bs.basic;

/**
 * @author cat
 * @description
 * @date 2023/9/15 20:10
 */

//描述
//一条长l的笔直的街道上有n个路灯，若这条街的起点为0，终点为l，第i个路灯坐标为ai ，每盏灯可以覆盖到的最远距离为d，
// 为了照明需求，所有灯的灯光必须覆盖整条街，但是为了省电，要使这个d最小，请找到这个最小的d。
//输入描述：
//每组数据第一行两个整数n和l（n大于0小于等于1000，l小于等于1000000000大于0）。
// 第二行有n个整数(均大于等于0小于等于l)，为每盏灯的坐标，多个路灯可以在同一点
import java.util.Scanner;
public class aaa {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {//注意while处理多个case
            int n = in.nextInt();
            int l = in.nextInt();
            int[] a = new int[n];
            int max = 0;
            for(int i = 0;i<n;i++){
                a[i] = in.nextInt();
            }
            sort(a);
            int star = 0;
            for(int i = 0;i<n;i++){
                int d = a[i]-star;
                star = a[i];
                max = d>max?d:max;
            }
            if(a[n-1]!=l){
                int r = l-a[n-1];//比较特殊终点不是一盏灯
                max = r*2>max?r*2:max;

            }
            String result = String.format("%.2f",max/2.00);
            System.out.println(result);
        }
    }

    public static void sort(int[] a){

        for(int i = 0;i<a.length-1;i++){
            boolean flag = true;
            for(int j = 0;j<a.length-1-i;j++){
                if(a[j]>a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    flag = false;
                }
            }
            if(flag)
                return;
        }
    }
}
