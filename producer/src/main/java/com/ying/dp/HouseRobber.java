package com.ying.dp;

import java.util.Stack;

/**
 * LeetCode 198. House Robber
 * https://leetcode.com/problems/house-robber/description/
 */
public class HouseRobber {

    public int rob(Integer[] nums) {
        int rob = 0, notrob = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = rob;
            rob = notrob + nums[i];
            notrob = Math.max(temp, notrob);
        }
        int max = Math.max(rob, notrob);
        return max;
    }

    public int rob2(Integer[] nums) {
        int pre1 = 0;
        int pre2 = 0;
        Stack<String> stacks = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = Math.max(nums[i] + pre2, pre1);
            if (nums[i] + pre2 > pre1) {
                stacks.push(String.valueOf(i));
            } else {
                if (!stacks.isEmpty()) {
                    stacks.pop();
                }
            }
            pre2 = pre1;
            pre1 = temp;
        }
        System.out.println(stacks);
        return pre1;
    }

    public static void main(String[] args) {
        HouseRobber houseRobber = new HouseRobber();
        Integer[] nums = {2, 11, 2, 1, 9, 8, 2};
        int rob1 = houseRobber.rob(nums);
        int rob2 = houseRobber.rob2(nums);
        System.out.println(String.format("%s, %s", rob1, rob2));
    }

}
