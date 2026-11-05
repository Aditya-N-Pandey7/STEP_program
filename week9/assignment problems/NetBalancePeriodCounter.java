import java.util.HashMap;
import java.util.Map;

public class NetBalancePeriodCounter {
    // Prefix sum frequency map: O(n) time, O(n) extra space.
    static long countPeriods(int[] transactions, int k) {
        Map<Long, Integer> frequency = new HashMap<>();
        frequency.put(0L, 1);

        long prefix = 0;
        long count = 0;

        for (int value : transactions) {
            prefix += value;
            count += frequency.getOrDefault(prefix - k, 0);
            frequency.put(prefix, frequency.getOrDefault(prefix, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};
        System.out.println(countPeriods(transactions, 7)); // 4
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10)); // 0
    }
}