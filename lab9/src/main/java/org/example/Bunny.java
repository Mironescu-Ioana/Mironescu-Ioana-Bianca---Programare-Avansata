package org.example;

import java.util.List;
import java.util.Random;

public class Bunny implements Runnable, Agent {
    private int row, col;
    private final GameController game;
    private final Random rand = new Random();

    private volatile int sleepTime = 600;
    private volatile boolean isPaused = false;

    public Bunny(int startRow, int startCol, GameController game) {
        this.row = startRow;
        this.col = startCol;
        this.game = game;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override public String getId() { return "bunny"; }
    @Override public void setSleepTime(int ms) { this.sleepTime = Math.max(100, ms); }
    @Override public int getSleepTime() { return sleepTime; }
    @Override public void setPaused(boolean paused) { this.isPaused = paused; }

    @Override
    public void run() {
        while (!game.isGameOver.get()) {
            try {
                //Thread.sleep(600);



                // Dacă e pus pe pauză, așteaptă și verifică din nou
                while (isPaused && !game.isGameOver.get()) {
                    Thread.sleep(100);
                }

                Thread.sleep(sleepTime);



                List<int[]> validMoves = game.getValidMoves(row, col);
                if (!validMoves.isEmpty()) {
                    int[] move = validMoves.get(rand.nextInt(validMoves.size()));
                    row = move[0];
                    col = move[1];
                }

                if (game.isExit(row, col)) {
                    System.out.println("Iepurasul a scapat cu viata!");
                    game.isGameOver.set(true);


                    System.exit(0);
                }

                game.printGameState(row, col);

            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}
