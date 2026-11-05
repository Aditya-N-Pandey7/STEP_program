public class MaxSumSubarrayFixedK {
    // Sliding window: O(n) time, O(1) extra space.
    // Naive recomputation takes O(n * k) time.
    static int maxSumSubarray(int[] sales, int k) {
        if (sales == null || k < 1 || k > sales.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        for (int i = k; i < sales.length; i++) {
            windowSum = windowSum - sales[i - k] + sales[i];
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;
        System.out.println(maxSumSubarray(sales, k)); // 9
    }
}