import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import repositories.DatabaseManager;

public class GameServer {
    public static final int PORT = 9300;
    private ServerSocket serverSocket;
    private boolean running = false;
    private ExecutorService threadPool;
    public QuizGame quizGame;

    public GameServer() {
        threadPool = Executors.newFixedThreadPool(10);
        quizGame = new QuizGame();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nSe inițiază oprirea grațioasă (Graceful Shutdown)...");
            stopServer();
        }));

        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
            System.out.println("Serverul a pornit si asteapta clienti pe portul " + PORT + "...");

            while (running) {

                Socket socket = serverSocket.accept();
                System.out.println("Un client nou s-a conectat!");

                threadPool.execute(new ClientThread(socket, this));
            }
        } catch (IOException e) {
            if (running) System.err.println("Eroare server: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
            System.out.println("Server oprit complet.");
        } catch (IOException e) {
            System.err.println("Eroare la închiderea socket-ului: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Așteptarea opririi thread-urilor a fost întreruptă!");
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();

            DatabaseManager.close();
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }
}
