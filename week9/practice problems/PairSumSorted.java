import java.util.Arrays;
import java.util.Scanner;

public class PairSumSorted {
    // Two pointers: O(n) time, O(1) extra space.
    // Brute force would take O(n^2) time.
    static int[] pairSumSorted(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                return new int[]{nums[left], nums[right]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {-4, -1, 0, 3, 5, 9};
        int target = 4;
        int[] result = pairSumSorted(nums, target);

        if (result == null) {
            System.out.println("Not Found");
        } else {
            System.out.println("(" + result[0] + ", " + result[1] + ")");
        }
    }
}