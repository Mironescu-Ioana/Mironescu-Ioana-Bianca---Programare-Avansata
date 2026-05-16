import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;
    private GameServer server;
    private Player player;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Bun venit! Tasteaza 'join <NumeleTau>' pentru a intra in joc.");

            String request;
            while ((request = in.readLine()) != null) {

                if (request.equalsIgnoreCase("exit")) {
                    break;
                }
                else if (request.equalsIgnoreCase("stop")) {
                    out.println("Se opreste serverul...");
                    server.stopServer();
                    break;
                }
                else if (request.startsWith("join ")) {
                    String name = request.substring(5);
                    player = new Player(name, out);
                    server.quizGame.addPlayer(player);
                    out.println("Te-ai alaturat cu succes! Cand toti sunt gata, tastati 'start'.");
                }
                else if (request.equalsIgnoreCase("start")) {
                    server.quizGame.start();
                }
                else if (player != null) {
                    server.quizGame.submitAnswer(player, request);
                }
                else {
                    out.println("Trebuie sa dai 'join' mai intai!");
                }
            }
        } catch (IOException e) {
            System.out.println("Client deconectat brusc.");
        } finally {
            if (player != null) {
                server.quizGame.removePlayer(player);
            }
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {
                System.err.println("Eroare la inchiderea socketului: " + e.getMessage());
            }
        }
    }
}