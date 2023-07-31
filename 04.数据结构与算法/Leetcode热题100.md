地址：https://leetcode.cn/studyplan/top-100-liked/



# 哈希

## 1.两数之和

### 题目描述

给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 **和为目标值** *`target`* 的那 **两个** 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

你可以按任意顺序返回答案。

### 问题分析

方法1：考虑暴力枚举法。注意的是，我们寻找和 `target - nums[i]` 匹配的值时，只用匹配 nums[i] 之后的值，因为之前的已经匹配过。

方法2：考虑哈希表 `HashMap<Integer, Integer>`。



### Solution

```java
public static int[] twoSum(int[] nums, int target) {
    int length = nums.length;
    int[] arr = new int[length];
    // 目标数组 [7, 2, -2, -6]
    for(int i = 0;i < length; i++){
        arr[i] = target - nums[i];
    }
    // 遍历
    int a = 0; int b = 0;
    for(int i = 0; i < length; i++){
        if (a != 0 && b != 0){
            break;
        }
        // nums[0] = 2
        for(int j = 0; j < length; j++){
            if(nums[i] == arr[j]){
                a = i;
                b = j;
                break;
            }
        }
    }
    System.out.println(a);
    System.out.println(b);
    return new int[]{a, b};
}
```



```java
public int[] twoSum2(int[] nums, int target) {
    HashMap<Integer, Integer> hashMap = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        if (hashMap.containsKey(target - nums[i])){
            return new int[]{hashMap.get(target - nums[i]), i};
        }
        hashMap.put(nums[i], i);
    }
    return new int[]{};
}
```





# 双指针





















