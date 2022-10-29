import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Lemonade
 * @description 插入区间：给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 * @updateTime 2022/10/20 13:33
 */
public class M57 {
    public static void main(String[] args) {
        int[][] intervals = {{1,3}, {6,9}};
        int[] newInterval = {2,5};
        System.out.println(Arrays.deepToString(new Solution57().insert(intervals, newInterval)));
    }
}

//输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
//输出：[[1,5],[6,9]]

//输入：intervals = [], newInterval = [5,7]
//输出：[[5,7]]
class Solution57 {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 特殊情况
        if (intervals.length == 0){
            return new int[][]{newInterval};
        }

        // 并集
        ArrayList<int[]> arrayList = new ArrayList<>();
        arrayList.add(newInterval);
        Collections.addAll(arrayList, intervals);
        int[][] ints = arrayList.toArray(new int[0][]);
        // 排序
        Arrays.sort(ints, Comparator.comparingInt(o -> o[0]));


        int[][] mergeInts = merge(ints);

        return mergeInts;

        //for (int i = 0; i < intervals.length; i++) {
        //    // [1,3],[6,9]      [2,3]
        //    // [1,3],[6,9]      [2,5]
        //    if (newInterval[0] <= intervals[i][1] && newInterval[1] < intervals[i + 1][0]){
        //        int[] arr = new int[]{intervals[i][1] , newInterval[1]};
        //        arrayList.add(arr);
        //    }
        //    // [1,3],[6,9]      [3,6]       [1,3],[6,9],[11,12]      [3,6]
        //    if (newInterval[0] == intervals[i][1] && newInterval[1] == intervals[i + 1][0]){
        //        int[] arr = new int[]{intervals[i][1] , intervals[i +1][1]};
        //        arrayList.add(arr);
        //    }
        //    // [1,3],[6,9]      [2,7]
        //    // [1,3],[6,9]      [2,10]
        //
        //
        //    // [1,3],[6,9]      [3,7]
        //    // [1,3],[6,9]      [3,5]
        //}
    }


    public int[][] merge(int[][] intervals) {
        //重写Arrays.sort()方法
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //这里根据第一列进行排序
                return o1[0] - o2[0];
            }
        });

        if (intervals.length == 1){
            return intervals;
        }

        int[][] mergeDp = mergeDp(intervals);

        while (check(mergeDp)){
            mergeDp = mergeDp(mergeDp);
        }
        return mergeDp;
    }

    // 是否不可再合并
    public boolean check(int[][] intervals){
        for (int i = 0; i < intervals.length - 1; i++) {
            int[] left = intervals[i];
            int[] right = intervals[i + 1];
            if (left[left.length - 1] >= right[0]){
                return true;
            }
        }
        return false;
    }

    public int[][] mergeDp(int[][] intervals) {

        ArrayList<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < intervals.length - 1; i++) {
            int[] left = intervals[i];
            int[] right = intervals[i + 1];

            int[] arr = new int[0];
            // 一共几种情况？考虑全面
            // 情况1：如果  [1,3],[2,6]，那么[1, 6]
            // 情况2：如果  [1,4],[0,4]，那么[0, 4]
            if (left[left.length - 1] >= right[0]){
                int leftN;
                int rightN;
                if (left[0] >= right[0]){
                    leftN = right[0];
                } else {
                    leftN = left[0];
                }
                if (left[left.length - 1] >= right[right.length - 1]){
                    rightN = left[right.length - 1];
                } else {
                    rightN = right[right.length - 1];
                }
                arr = new int[]{leftN, rightN};
                //原数组未改变，死循环
                //--i;
                ++i;
            } else {
                arr = intervals[i];
            }
            arrayList.add(arr);
        }
        int[] ints = arrayList.get(arrayList.size() - 1);
        if (ints[ints.length - 1] < intervals[intervals.length - 1][intervals[0].length - 1]){
            arrayList.add(intervals[intervals.length - 1]);
        }
        return arrayList.toArray(new int[0][]);
    }
}





//0 <= intervals.length <= 104
//intervals[i].length == 2
//newInterval.length == 2

//0 <= intervals[i][0] <= intervals[i][1] <= 105
//intervals 根据 intervals[i][0] 按 升序 排列
//0 <= newInterval[0] <= newInterval[1] <= 105
