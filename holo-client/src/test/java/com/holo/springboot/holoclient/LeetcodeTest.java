package com.holo.springboot.holoclient;

import org.junit.jupiter.api.Test;

public class LeetcodeTest {

    @Test
    public void removeDuplicates() {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        int i = this.removeDuplicates(nums);
        for (int j = 0; j < nums.length; j++) {
            System.out.print(nums[j]+"、");
        }
        System.out.println(String.format("长度为：%s",i));
    }


    public int removeDuplicates(int[] nums) {
        if(nums == null ||nums.length==0){
           return 0;
        }
        int left = 0;
        for (int right = 1; right < nums.length; right++) {
            if (nums[left] == nums[right]) {
                System.out.println(String.format("发现重复元素：%s", nums[right]));
            }else {
                System.out.println(String.format("发现新元素：%s", nums[right]));
                nums[++left]=nums[right];
            }
        }

        return ++left;
    }


    @Test
    public void rotate() {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        rotate(nums,3);
    }

    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int copyNums[] = new int[length];
        for (int i = 0; i < nums.length; i++) {
            copyNums[i] = nums[i];
        }
        for (int i = 0; i < length; i++) {
            nums[(i + k) % length] = copyNums[i];
        }
        for (int i = 0; i < length; i++) {
            System.out.println(nums[i]);
        }
    }
}
