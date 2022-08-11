import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/12 下午11:37
 */
public class M1143 {
    public static void main(String[] args) {
        String text1 = "oxcpqrsvwf";
        String text2 = "shmtulqrypy";
        //"bsbininm"
        //"jmjkbkjkv"

        //"oxcpqrsvwf"
        //"shmtulqrypy"

        int count = new Solution11432().longestCommonSubsequence(text1, text2);
        System.out.println(count);
    }
}

//M1143. 最长公共子序列
//https://leetcode.cn/problems/longest-common-subsequence/

//输入：text1 = "abcde", text2 = "ace"
//输出：3
//解释：最长公共子序列是 "ace" ，它的长度为 3 。


//"ezupkr"
//"ubmrapg"
//class Solution1143 {
//    public int longestCommonSubsequence(String text1, String text2) {
//        int count = 0;
//        char c;
//        int k2 = 0;
//
//        for (int i = 0; i < text1.length(); i++) {
//            c = text1.charAt(i);
//            for (int j = k2; j < text2.length(); j++) {
//
//                if (c == text2.charAt(j)) {
//                    count++;
//                    k2 = j + 1;
//                    System.out.println("k2->" + k2);
//                    break;
//                }
//                //count = 0;
//            }
//        }
//        return count;
//    }
//}


class Solution11432 {
    int[][] memo;

    public int longestCommonSubsequence(String text1, String text2) {
        memo = new int[text1.length()][text2.length()];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }

        return dp(text1, 0, text2, 0);
    }

    int dp(String s1, int i, String s2, int j) {
        // base case
        if (i == s1.length() || j == s2.length()){
            return 0;
        }

        if (memo[i][j] != -1){
            return memo[i][j];
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + dp(s1, i + 1, s2, j + 1);
        } else {
            memo[i][j] = max(
                    dp(s1, i + 1, s2, j),
                    dp(s1, i, s2, j + 1),
                    dp(s1, i + 1, s2, j + 1)
            );
        }
        return memo[i][j];
    }

    int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }
}