public class WarehouseBinGridScan {
    // Time: O(m * n), extra space: O(1).
    // On ties, the first cell in row-major order is retained.
    static void warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            System.out.println("Empty grid");
            return;
        }

        int total = 0;
        int max = grid[0][0];
        int maxRow = 0;
        int maxCol = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                total += grid[row][col];

                if (grid[row][col] > max) {
                    max = grid[row][col];
                    maxRow = row;
                    maxCol = col;
                }
            }
        }

        System.out.println("Total = " + total);
        System.out.println("Max Coordinate = (" + maxRow + ", " + maxCol + ")");
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };
        warehouseSummary(grid);
    }
}