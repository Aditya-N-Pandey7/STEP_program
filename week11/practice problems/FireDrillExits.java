import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FireDrillExits {
    // Multi-source BFS: O(rows * cols) time and O(rows * cols) space.
    // E = exit, # = wall, . = open room.
    static int[][] exitDistance(char[][] plan) {
        if (plan == null || plan.length == 0 || plan[0].length == 0) {
            return new int[0][0];
        }

        int rows = plan.length;
        int cols = plan[0].length;
        int[][] distance = new int[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();

        for (int r = 0; r < rows; r++) {
            Arrays.fill(distance[r], -1);
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    distance[r][c] = 0;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int direction = 0; direction < 4; direction++) {
                int nr = r + dr[direction];
                int nc = c + dc[direction];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && plan[nr][nc] != '#'
                        && distance[nr][nc] == -1) {
                    distance[nr][nc] = distance[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return distance;
    }

    static void printGrid(int[][] distance) {
        for (int[] row : distance) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };
        printGrid(exitDistance(plan));
    }
}