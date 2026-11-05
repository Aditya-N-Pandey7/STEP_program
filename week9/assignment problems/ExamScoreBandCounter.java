public class ExamScoreBandCounter {
    // Two binary searches: O(log n) time and O(1) extra space.
    // Find first score >= low and first score > high.
    static int countInBand(int[] scores, int low, int high) {
        int firstAtLeastLow = lowerBound(scores, low);
        int firstGreaterHigh = upperBound(scores, high);
        return firstGreaterHigh - firstAtLeastLow;
    }

    private static int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println(countInBand(scores, 42, 58)); // 6
        System.out.println(countInBand(scores, 90, 100)); // 0
    }
}