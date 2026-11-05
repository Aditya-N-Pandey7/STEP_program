public class LongestBudgetFriendlyStreak {
    // Sliding window: O(n) time and O(1) extra space.
    // Non-negative costs allow the window sum to decrease when left advances.
    static int[] longestStreak(int[] costs, int budget) {
        int left = 0;
        int sum = 0;
        int bestLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (left <= right && sum > budget) {
                sum -= costs[left];
                left++;
            }

            int length = right - left + 1;
            if (length > bestLength) {
                bestLength = length;
                bestStart = left;
            }
        }

        return new int[]{bestLength, bestStart};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int[] result = longestStreak(costs, 8);
        System.out.println("(" + result[0] + ", " + result[1] + ")");

        int[] second = longestStreak(new int[]{9, 10}, 8);
        System.out.println("(" + second[0] + ", " + second[1] + ")");
    }
}