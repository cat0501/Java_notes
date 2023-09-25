package com.bs.basic;

/**
 * @author cat
 * @description
 * @date 2023/9/16 14:02
 */
// 你测试哈
public class A0916 {
    public int maxProfit (int[] prices) {
        int max = 0;
        int a = prices[0];
        int b;
        for(int i=1;i<prices.length;i++){
            b = prices[i];
            if(b > a){//字体小点、需要翻译为ACM嘛？？？我给你找下一题       牛客原题
                max += (b-a);
                a = prices[i];
            }
            else a = b;
        }
        return max;
    }
}


















