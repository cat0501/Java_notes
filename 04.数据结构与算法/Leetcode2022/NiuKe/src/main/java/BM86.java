/**
 * @author cat
 * @description
 * @date 2022/6/13 下午11:57
 */
public class BM86 {
}


class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 计算两个数之和
     * @param s string字符串 表示第一个整数
     * @param t string字符串 表示第二个整数
     * @return string字符串
     */
    public String solve (String s, String t) {
        // write code here
        if (s.length() <= 0){
            return t;
        }
        if (t.length() <= 0){
            return s;
        }
        // s为长的
        if (s.length() < t.length()){
            String tmp = s;
            s = t;
            t = tmp;
        }



        return null;
    }
}