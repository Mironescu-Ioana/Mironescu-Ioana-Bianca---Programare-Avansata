import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int PORT = 9300;

        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectat la serverul jocului!");
            System.out.println("Introdu comenzi (sau scrie 'exit' pentru a iesi):");

            // THREAD 1: Ascultă mesajele de la server în fundal
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("\n[SERVER] " + serverMsg);
                        System.out.print("> "); // Reprintează cursorul ca să știi unde scrii
                    }
                } catch (IOException e) {
                    System.out.println("\nConexiunea cu serverul a fost închisă.");
                }
            });
            listenerThread.start();

            // THREAD 2 (Main): Citește input-ul tău de la tastatură
            while (true) {
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    System.out.println("Închidere client...");
                    break;
                }
                out.println(command); // Trimite către server
            }

        } catch (Exception e) {
            System.err.println("Eroare la client: " + e.getMessage());
        }
    }
}