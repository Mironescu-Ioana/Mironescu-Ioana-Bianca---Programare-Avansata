package org.example;

public class TimeDaemon implements Runnable {
    private final GameController game;
    private final Bunny bunny;
    private final long maxDurationMs;
    private final long startTime;

    public TimeDaemon(GameController game, Bunny bunny, int maxSeconds) {
        this.game = game;
        this.bunny = bunny;
        this.maxDurationMs = maxSeconds * 1000L;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (!game.isGameOver.get()) {
            try {
                Thread.sleep(1000); // Actualizează o dată pe secundă

                long elapsed = System.currentTimeMillis() - startTime;

                // Desenează tabla
                System.out.println("\nTimp scurs: " + (elapsed / 1000) + "s / " + (maxDurationMs / 1000) + "s");
                game.printGameState(bunny.getRow(), bunny.getCol());

                // Verifică limita de timp
                if (elapsed >= maxDurationMs) {
                    System.out.println("\n! Timpul a expirat! Iepurașul nu a fost prins, dar nici nu a scăpat !");
                    game.isGameOver.set(true);
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
