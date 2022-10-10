import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Lemonade
 * @description 合并区间
 * @updateTime 2022/10/10 16:34
 */

public class M56 {
    public static void main(String[] args) {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};

        System.out.println(Arrays.deepToString(new Solution56().merge(intervals)));
    }
}


//输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
//输出：[[1,6],[8,10],[15,18]]
//解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].


class Solution56 {
    public int[][] merge(int[][] intervals) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < intervals.length - 1; i++) {
            int[] left = intervals[i];
            int[] right = intervals[i + 1];

            int[] arr = new int[0];
            if (left[left.length - 1] > right[0]){
                int leftN = left[0];
                int rightN = right[right.length - 1];
                arr = new int[]{leftN, rightN};
                //原数组未改变，死循环
                //--i;
            } else {
                arr = intervals[i];
            }
            arrayList.add(arr);
        }

        arrayList.add(intervals[intervals.length - 1]);
        return arrayList.toArray(new int[0][]);
    }

    public int[][] mergeDp(int[][] intervals) {

        ArrayList<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < intervals.length - 1; i++) {
            int[] left = intervals[i];
            int[] right = intervals[i + 1];

            int[] arr = new int[0];
            if (left[left.length - 1] > right[0]){
                int leftN = left[0];
                int rightN = right[right.length - 1];
                arr = new int[]{leftN, rightN};
                //原数组未改变，死循环
                //--i;
            } else {
                arr = intervals[i];
            }
            arrayList.add(arr);
        }

        arrayList.add(intervals[intervals.length - 1]);
        return arrayList.toArray(new int[0][]);
    }
}


//class Solution56 {
//    public int[][] merge(int[][] intervals) {
//        //int[][] result = new int[][]{};
//        ArrayList<int[]> arrayList = new ArrayList<>();
//        for (int i = 0; i < intervals.length - 1; i++) {
//            int[] left = intervals[i];
//            int[] right = intervals[i + 1];
//
//            int[] arr = new int[0];
//            if (left[left.length - 1] > right[0]){
//                int leftN = left[0];
//                int rightN = right[right.length - 1];
//                arr = new int[]{leftN, rightN};
//                //原数组未改变，死循环
//                //--i;
//            } else {
//                arr = intervals[i];
//            }
//            arrayList.add(arr);
//        }
//
//        arrayList.add(intervals[intervals.length - 1]);
//        return arrayList.toArray(new int[0][]);
//    }
//}


//输入
//[[1,3],[2,6],[8,10],[15,18]]
//输出
//[[1,6],[2,6],[8,10],[15,18]]
//预期结果
//[[1,6],[8,10],[15,18]]



//class Solution56 {
//    public int[][] merge(int[][] intervals) {
//        //int[][] result = new int[][]{};
//        ArrayList<int[]> arrayList = new ArrayList<>();
//        for (int i = 0; i < intervals.length - 1; i++) {
//            int[] left = intervals[i];
//            int[] right = intervals[i + 1];
//
//            int[] arr = new int[0];
//            if (left[left.length - 1] > right[0]){
//                int leftN = left[0];
//                int rightN = right[right.length - 1];
//                arr = new int[]{leftN, rightN};
//            } else {
//                arr = intervals[i];
//            }
//            arrayList.add(arr);
//        }
//
//        arrayList.add(intervals[intervals.length - 1]);
//        return arrayList.toArray(new int[0][]);
//    }
//}

