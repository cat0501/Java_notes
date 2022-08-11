import java.util.Arrays;
import java.util.HashMap;

/**
 * @author cat
 * @description
 * @date 2022/6/15 下午12:15
 */
public class M3 {
    public static void main(String[] args) {

        //HashMap<Character, Integer> map = new HashMap<>();
        //map.put('a', 1);
        //map.put('a', 2);
        //System.out.println(map);

        System.out.println(new Solution3().lengthOfLongestSubstring("abcabcbb"));
    }

}
//https://leetcode.cn/problems/longest-substring-without-repeating-characters/

//输入: s = "abcabcbb"
//输出: 3
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        int[] dp = new int[s.length()];
        if (s.length() == 0){
            return 0;
        }

        dp[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) != s.charAt(j)){
                    dp[i] += 1;
                } else {
                    break;
                }
            }
            dp[i] += 1;

        }

        Arrays.sort(dp);
        return dp[dp.length - 1];
    }
}


class Solution31 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;//滑动窗口左指针

        for (int i = 0; i < s.length(); i++) {
            //1、首先，判断当前字符是否包含在map中，如果不包含，将该字符添加到map（字符，字符在数组下标）

            //2、如果当前字符 ch 包含在 map中，此时有2类情况：
            //1）当前字符包含在当前有效的子段中，如：abca，当我们遍历到第二个a，当前有效最长子段是 abc，我们又遍历到a，
            //             那么此时更新 left 为 map.get(a)+1=1，当前有效子段更新为 bca；

            //2）当前字符不包含在当前最长有效子段中，如：abba，我们先添加a,b进map，此时left=0，我们再添加b，发现map中包含b，
            // 而且b包含在最长有效子段中，就是1）的情况，我们更新 left=map.get(b)+1=2，此时子段更新为 b，而且map中仍然包含a，map.get(a)=0；
            // 随后，我们遍历到a，发现a包含在map中，且map.get(a)=0，如果我们像1）一样处理，就会发现 left=map.get(a)+1=1，实际上，left此时
            // 应该不变，left始终为2，子段变成 ba才对。

            //为了处理以上2类情况，我们每次更新left，left=Math.max(left , map.get(ch)+1).
            //             另外，更新left后，不管原来的 s.charAt(i) 是否在最长子段中，我们都要将 s.charAt(i) 的位置更新为当前的i，
            //             因此此时新的 s.charAt(i) 已经进入到 当前最长的子段中！
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }

            //不管是否更新left，都要更新 s.charAt(i) 的位置！
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left  +1);
        }

        return max;
    }
}