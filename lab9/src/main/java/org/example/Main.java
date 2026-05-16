package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("START JOC !\n");
        System.out.println("Comenzi disponibile: 'pause all', 'resume r1', 'speedup bunny', 'slowdown all', 'exit'");

        int rows = 7;
        int cols = 7;
        int exitRow = rows - 1;
        int exitCol = cols - 1;

        GameController game = new GameController(rows, cols, exitRow, exitCol);
        SharedMemory sharedMemory = new SharedMemory();

        Bunny bunny = new Bunny(0, 0, game);

        List<Agent> agents = new ArrayList<>();
        agents.add(bunny);

        Random rand = new Random();
        Thread[] robotThreads = new Thread[3];

        //se spawneaza robotii random
        for (int i = 0; i < 3; i++) {
            int r, c;
            do {
                r = rand.nextInt(rows);
                c = rand.nextInt(cols);
            } while ((r == 0 && c == 0) || (r == exitRow && c == exitCol));

            Robot bot = new Robot("R" + (i + 1), r, c, game, sharedMemory, bunny);
            agents.add(bot);
            robotThreads[i] = new Thread(bot);
        }

        //game.printGameState(bunny.getRow(), bunny.getCol());

        //Pornim Daemon-ul
        Thread daemonThread = new Thread(new TimeDaemon(game, bunny, 60));
        daemonThread.setDaemon(true); // Îl setăm ca daemon
        daemonThread.start();

        new Thread(bunny).start();
        for (Thread t : robotThreads)
            t.start();


        Scanner scanner = new Scanner(System.in);
        while (!game.isGameOver.get()) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().toLowerCase().trim();
                String[] parts = input.split(" ");
                if (parts.length < 1) continue;

                String cmd = parts[0];
                String targetId;

                if (parts.length > 1) {
                    targetId = parts[1];
                } else {
                    targetId = "";

                }

                if (cmd.equals("exit")) {
                    System.exit(0);
                }

                for (Agent agent : agents) {
                    if (targetId.equals("all") || targetId.equals(agent.getId())) {
                        switch (cmd) {
                            case "pause":
                                agent.setPaused(true);
                                System.out.println("[CMD] " + agent.getId() + " este în pauză.");
                                break;
                            case "resume":
                                agent.setPaused(false);
                                System.out.println("[CMD] " + agent.getId() + " a reluat mișcarea.");
                                break;
                            case "speedup":
                                agent.setSleepTime(agent.getSleepTime() - 200);
                                System.out.println("[CMD] " + agent.getId() + " a accelerat.");
                                break;
                            case "slowdown":
                                agent.setSleepTime(agent.getSleepTime() + 400);
                                System.out.println("[CMD] " + agent.getId() + " a încetinit.");
                                break;
                        }
                    }
                }
            }
        }
    }
}
