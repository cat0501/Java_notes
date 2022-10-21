/**
 * @author Lemonade
 * @description 最后一个单词的长度
 * @updateTime 2022/10/20 14:22
 */
public class E58 {
    public static void main(String[] args) {
        String s = "Hello World";
        String s2 = "   fly me   to   the moon  ";
        System.out.println(new Solution58B().lengthOfLastWord(s2));
    }
}

//输入：s = "Hello World"
//输出：5
//解释：最后一个单词是“World”，长度为5。

//输入：s = "   fly me   to   the moon  "
//输出：4
//解释：最后一个单词是“moon”，长度为4。
class Solution58 {
    public int lengthOfLastWord(String s) {
        String[] splitStr = s.trim().split(" ");
        return splitStr[splitStr.length - 1].length();
    }
}

class Solution58B {
    public int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        while (end > 0 && s.charAt(end) == ' ') end--;
        int start = end;
        while (start > 0 && s.charAt(start) != ' ') start--;
        return end - start;
    }
}

