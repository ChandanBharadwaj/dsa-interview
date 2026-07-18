import java.util.*;

public class SnakesAndLadders {

    // The board is an implicit graph: each square has up to 6 dice edges,
    // rerouted by snakes/ladders. Fewest moves = BFS on squares.
    public static int snakesAndLadders(int[][] board) {
        int n = board.length;
        boolean[] visited = new boolean[n * n + 1];
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(1);
        visited[1] = true;
        int moves = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int square = q.poll();
                if (square == n * n) return moves;
                for (int die = 1; die <= 6 && square + die <= n * n; die++) {
                    int next = square + die;
                    int dest = value(board, n, next);       // snake/ladder reroute
                    int land = dest == -1 ? next : dest;
                    if (!visited[land]) {
                        visited[land] = true;
                        q.offer(land);
                    }
                }
            }
            moves++;
        }
        return -1;
    }

    // Square number -> board cell: row 0 of numbering is the BOTTOM row,
    // and rows alternate direction (boustrophedon).
    private static int value(int[][] board, int n, int square) {
        int idx = square - 1;
        int rowFromBottom = idx / n;
        int r = n - 1 - rowFromBottom;
        int c = idx % n;
        if (rowFromBottom % 2 == 1)                 // odd rows run right-to-left
            c = n - 1 - c;
        return board[r][c];
    }

    public static void main(String[] args) {
        int[][] b = {
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, 35, -1, -1, 13, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, 15, -1, -1, -1, -1}};
        assert snakesAndLadders(b) == 4;
        assert snakesAndLadders(new int[][]{{-1, -1}, {-1, 3}}) == 1;
        System.out.println("SnakesAndLadders: OK");
    }
}
