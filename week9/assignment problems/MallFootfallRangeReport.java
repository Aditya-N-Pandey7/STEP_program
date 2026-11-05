import java.util.Arrays;

public class MallFootfallRangeReport {
    // Prefix sums: preprocessing O(n), each query O(1).
    // Total time O(n + q), extra space O(n).
    static int[] footfallReport(int[] visitors, int[][] queries) {
        int[] prefix = new int[visitors.length + 1];

        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        int[] answers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            answers[i] = prefix[end + 1] - prefix[start];
        }
        return answers;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};
        System.out.println(Arrays.toString(footfallReport(visitors, queries)));
    }
}