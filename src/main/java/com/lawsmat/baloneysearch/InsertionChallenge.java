package com.lawsmat.baloneysearch;

import java.util.Arrays;

public class InsertionChallenge {
    public static void main(String[] args) {
        int[] nums = { 4, 10, 23, 12, 3, 6, 33, 40, 37 };
        Arrays.sort(nums);
        int mid = nums.length / 2;
        Bs<Integer> numTree = new Bs<>(nums[mid]);
        insert(numTree, nums, 0, mid - 1);
        insert(numTree, nums, mid + 1, nums.length - 1);
        System.out.println("Result:");
        numTree.print();
    }

    public static void insert(Bs<Integer> tree, int[] arr, int start, int end) {
        if(start <= end) {
            int middle = start + (end - start) / 2;
            tree.insert(arr[middle]);
            insert(tree, arr, middle + 1, end);
            insert(tree, arr, start, middle - 1);
        }
    }
}
