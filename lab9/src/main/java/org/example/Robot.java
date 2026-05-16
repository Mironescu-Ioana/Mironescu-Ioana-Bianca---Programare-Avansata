package org.example;

import java.util.*;

public class Robot implements Runnable, Agent {
    private int row, col;
    private String id;
    private GameController game;
    private SharedMemory memory;
    private Bunny targetBunny;
    private Random rand = new Random();

    private Set<String> visitedCells = new HashSet<>();

    private volatile int sleepTime = 500;
    private volatile boolean isPaused = false;

    public Robot(String id, int startRow, int startCol, GameController game, SharedMemory memory, Bunny target) {
        this.id = id;
        this.row = startRow;
        this.col = startCol;
        this.game = game;
        this.memory = memory;
        this.targetBunny = target;
        game.tryMoveRobot(-1, -1, startRow, startCol);
        visitedCells.add(startRow + "," + startCol);
    }

    @Override public String getId() { return id.toLowerCase(); }
    @Override public void setSleepTime(int ms) { this.sleepTime = Math.max(100, ms); }
    @Override public int getSleepTime() { return sleepTime; }
    @Override public void setPaused(boolean paused) { this.isPaused = paused; }

    @Override
    public void run() {
        while (!game.isGameOver.get()) {
            try {
                //Thread.sleep(500);


                while (isPaused && !game.isGameOver.get()) {
                    Thread.sleep(100);
                }
                Thread.sleep(sleepTime);



                List<int[]> validMoves = game.getValidMoves(row, col);
//                if (!validMoves.isEmpty()) {
//                    int[] move = validMoves.get(rand.nextInt(validMoves.size()));
//                    if (game.tryMoveRobot(row, col, move[0], move[1])) {
//                        row = move[0];
//                        col = move[1];
//                    }
//                }


                //caut celulele nevizitate
                List<int[]> unvisited = new ArrayList<>();
                for (int[] move : validMoves) {
                    if (!visitedCells.contains(move[0] + "," + move[1])) {
                        unvisited.add(move);
                    }
                }

                //daca nu mai poate merge in celule noi, se duce random in cele deja vizitate
                int[] chosenMove;
                if (!unvisited.isEmpty()) {
                    chosenMove = unvisited.get(rand.nextInt(unvisited.size()));
                } else if (!validMoves.isEmpty()) {
                    chosenMove = validMoves.get(rand.nextInt(validMoves.size()));
                } else continue;

                if (game.tryMoveRobot(row, col, chosenMove[0], chosenMove[1])) {
                    row = chosenMove[0];
                    col = chosenMove[1];
                    visitedCells.add(row + "," + col);
                }




                if (row == targetBunny.getRow() && col == targetBunny.getCol()) {
                    System.out.println("! Robotul " + id + " a prins iepurasul! Iepurasul e mort !");
                    game.isGameOver.set(true);
                    System.exit(0);
                }

                if (Math.abs(row - targetBunny.getRow()) <= 1 && Math.abs(col - targetBunny.getCol()) <= 1) {
                    memory.updateBunnyPosition(targetBunny.getRow(), targetBunny.getCol());
                }

            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}
