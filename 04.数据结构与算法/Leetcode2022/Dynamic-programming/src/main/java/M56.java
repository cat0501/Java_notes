import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Lemonade
 * @description 合并区间
 * @updateTime 2022/10/10 16:34
 */

public class M56 {

    //public static void main(String args[]){
    //    int intervals[][] = {{2,6},{1,3},{8,10},{15,18}};
    //
    //    System.out.println("排序前");
    //    for (int i = 0; i < intervals.length; i++){
    //        for (int j = 0; j < 2; j++){
    //            System.out.print(intervals[i][j]+" ");
    //        }
    //        System.out.println();
    //    }
    //
    //    //重写Arrays.sort()方法
    //    Arrays.sort(intervals, new Comparator<int[]>() {
    //        @Override
    //        public int compare(int[] o1, int[] o2) {
    //            //这里根据第一列进行排序
    //            return o1[0] - o2[0];
    //        }
    //    });
    //
    //    System.out.println("排序后");
    //    for (int i = 0; i < intervals.length; i++){
    //        for (int j = 0; j < 2; j++){
    //            System.out.print(intervals[i][j] +" ");
    //        }
    //        System.out.println();
    //    }
    //}



    public static void main(String[] args) {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] intervals2 = {{1,4},{4,5}};
        int[][] intervals3 = {{1,3}};
        int[][] intervals4 = {{1,4},{0,4}};
        int[][] intervals5 = {{1,4},{0,1}};
        // 118 / 170 个通过的测试用例
        int[][] intervals6 = {{1,4},{0,0}};
        int[][] intervals7 = {{4,5},{1,4},{0,1}};


        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals)));
        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals2)));
        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals3)));
        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals4)));
        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals5)));
        //System.out.println(Arrays.deepToString(new Solution56().merge(intervals6)));
        System.out.println(Arrays.deepToString(new Solution56().merge(intervals7)));
    }
}


//输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
//输出：[[1,6],[8,10],[15,18]]
//解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].


class Solution56 {
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

