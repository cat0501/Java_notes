package niuke;

/**
 * @author cat
 * @description
 * @date 2022/4/13 下午8:01
 */

/**
 描述
 有一个长度为 n 的非降序数组，比如[1,2,3,4,5]，将它进行旋转，即把一个数组最开始的若干个元素搬到数组的末尾，变成一个旋转数组，
 比如变成了[3,4,5,1,2]，或者[4,5,1,2,3]这样的。请问，给定这样一个旋转数组，求数组中的最小值。
 */
public class BM21 {

    public static void main(String[] args) {

        int[] array = {1,2,3,4,5};
        System.out.println(minNumberInRotateArray(array));

    }

    public static int minNumberInRotateArray(int [] array) {
        int size = array.length;

        int min = array[0];
        for (int i = 1; i < size; i++) {
            if (array[i] < min){
                min = array[i];
            }
        }
        return min;
    }
}
