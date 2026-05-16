package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {
    private final int rows, cols;
    private final int exitRow, exitCol;
    private Cell[][] grid;
    private final boolean[][] robotPositions;
    public final AtomicBoolean isGameOver = new AtomicBoolean(false);

    public GameController(int rows, int cols, int exitRow, int exitCol) {
        this.rows = rows;
        this.cols = cols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.robotPositions = new boolean[rows][cols];

        generateValidMaze();
    }

    private void generateValidMaze() {
        boolean isValid = false;
        Random rand = new Random();
        System.out.println("Generare labirint valid...");

        while (!isValid) {
            grid = new Cell[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = new Cell(i, j);
                }
            }

            //elimina pereti random
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (rand.nextBoolean()) { grid[i][j].top = false; if (i > 0) grid[i - 1][j].bottom = false; }
                    if (rand.nextBoolean()) { grid[i][j].right = false; if (j < cols - 1) grid[i][j + 1].left = false; }
                    if (rand.nextBoolean()) { grid[i][j].bottom = false; if (i < rows - 1) grid[i + 1][j].top = false; }
                    if (rand.nextBoolean()) { grid[i][j].left = false; if (j > 0) grid[i][j - 1].right = false; }
                }
            }

            isValid = isMazeValidBfs();
        }
        System.out.println("Labirint generat cu succes!\n");
    }

    private boolean isMazeValidBfs() {
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.add(grid[0][0]);
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            if (curr.row == exitRow && curr.col == exitCol) return true;

            int r = curr.row, c = curr.col;
            if (!curr.top && r > 0 && !visited[r - 1][c]) { visited[r - 1][c] = true; queue.add(grid[r - 1][c]); }
            if (!curr.bottom && r < rows - 1 && !visited[r + 1][c]) { visited[r + 1][c] = true; queue.add(grid[r + 1][c]); }
            if (!curr.left && c > 0 && !visited[r][c - 1]) { visited[r][c - 1] = true; queue.add(grid[r][c - 1]); }
            if (!curr.right && c < cols - 1 && !visited[r][c + 1]) { visited[r][c + 1] = true; queue.add(grid[r][c + 1]); }
        }
        return false;
    }

    public List<int[]> getValidMoves(int r, int c) {
        List<int[]> moves = new ArrayList<>();
        if (!grid[r][c].top && r > 0) moves.add(new int[]{r - 1, c});
        if (!grid[r][c].bottom && r < rows - 1) moves.add(new int[]{r + 1, c});
        if (!grid[r][c].left && c > 0) moves.add(new int[]{r, c - 1});
        if (!grid[r][c].right && c < cols - 1) moves.add(new int[]{r, c + 1});
        return moves;
    }

    public synchronized boolean tryMoveRobot(int oldRow, int oldCol, int newRow, int newCol) {
        if (isGameOver.get() || robotPositions[newRow][newCol]) return false;
        if (oldRow >= 0 && oldCol >= 0) robotPositions[oldRow][oldCol] = false;
        robotPositions[newRow][newCol] = true;
        return true;
    }

    public synchronized void printGameState(int bunnyRow, int bunnyCol) {
        if (isGameOver.get()) return;

        System.out.println("Stare labirint:");
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j].top ? "+---" : "+   ");
            }
            System.out.println("+");


            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j].left ? "|" : " ");

                if (i == bunnyRow && j == bunnyCol) System.out.print(" \uD83D\uDC30");
                else if (robotPositions[i][j]) System.out.print(" \uD83E\uDD16");
                else if (i == exitRow && j == exitCol) System.out.print(" \uD83D\uDEAA");
                else System.out.print("   ");
            }
            System.out.println(grid[i][cols - 1].right ? "|" : " ");
        }

        for (int j = 0; j < cols; j++) {
            System.out.print(grid[rows - 1][j].bottom ? "+---" : "+   ");
        }
        System.out.println("+\n-------------------------");
    }

    public boolean isExit(int r, int c) { return r == exitRow && c == exitCol; }
}
