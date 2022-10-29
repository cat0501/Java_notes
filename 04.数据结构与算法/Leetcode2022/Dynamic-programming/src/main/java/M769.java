/**
 * @author Lemonade
 * @description
 * @updateTime 2022/10/13 16:46
 */


public class M769 {
    public static void main(String[] args) {
        int[] arr = {4,3,2,1,0};
        int[] arr2 = {1,0,2,3,4};
        System.out.println(new Solution769().maxChunksToSorted(arr));
        System.out.println(new Solution769().maxChunksToSorted(arr2));
    }

}


//输入: arr = [4,3,2,1,0]
//输出: 1
//解释:
//将数组分成2块或者更多块，都无法得到所需的结果。
//例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。


//输入: arr = [1,0,2,3,4]
//输出: 4
//解释:
//我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
//然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。

class Solution769 {
    public int maxChunksToSorted(int[] arr) {
        int count = 0;
        int vSum = 0;
        int iSum = 0;

        for (int i = 0; i < arr.length; i++) {
            vSum += arr[i];
            iSum += i;
            if (vSum == iSum){
                count++;
            }
        }

        return count;
    }
}

/**
 * 贪心解法（官解）
 */
class Solution769A {
    public int maxChunksToSorted(int[] arr) {
        int m = 0, res = 0;

        for (int i = 0; i < arr.length; i++) {
            m = Math.max(m, arr[i]);
            if (m == i) {
                res++;
            }
        }
        return res;
    }
}
