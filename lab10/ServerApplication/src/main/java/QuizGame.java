import java.util.*;
import models.*;
import repositories.*;

public class QuizGame {
    private List<String[]> questions = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private volatile boolean isRunning = false;
    private volatile long questionStartTime;

    public QuizGame() {
        loadQuestions();
    }

    private void loadQuestions() {
        QuestionRepository repo = new QuestionRepository();
        List<Question> dbQuestions = repo.findAll();

        if (dbQuestions.isEmpty()) {
            System.out.println("[DB] Baza de date este goală. Inserez întrebări default...");
            repo.create(new Question("În ce an s-a nascut Ioana Mironescu?", "2006"));
            repo.create(new Question("Cate pisici are Ioana Mironescu?", "2"));
            repo.create(new Question("Care este mâncarea preferata a Ioanei Mironescu?", "Bors"));

            dbQuestions = repo.findAll();
        }

        for (Question q : dbQuestions) {
            questions.add(new String[]{ q.getText(), q.getCorrectAnswer() });
        }
        System.out.println("[DB] S-au încărcat " + questions.size() + " întrebări din baza de date!");
    }

    public synchronized void addPlayer(Player player) {
        players.add(player);
        broadcast("Jucătorul " + player.getName() + " s-a alăturat jocului!");
    }

    public synchronized void removePlayer(Player player) {
        if (player != null) {
            players.remove(player);
            broadcast("Jucătorul " + player.getName() + " a părăsit jocul.");
        }
    }

    public synchronized void submitAnswer(Player player, String answer) {
        if (!isRunning || player.getCurrentAnswer() != null) return;

        long timeTaken = System.currentTimeMillis() - questionStartTime;
        player.setCurrentAnswer(answer, timeTaken);
        player.sendMessage("Răspuns înregistrat în " + (timeTaken / 1000.0) + " secunde.");
    }

    public void broadcast(String message) {
        for (Player p : players) p.sendMessage(message);
    }

    public void start() {
        if (isRunning || players.isEmpty()) return;

        new Thread(() -> {
            isRunning = true;
            broadcast("=== JOCUL ÎNCEPE ÎN 3 SECUNDE ===");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}

            for (String[] q : questions) {
                for (Player p : players) p.resetForNewQuestion();

                String questionText = q[0];
                String correctAnswer = q[1];

                broadcast("\nÎNTREBARE: " + questionText);
                broadcast("Ai 10 secunde să răspunzi!");

                questionStartTime = System.currentTimeMillis();

                try { Thread.sleep(10000); } catch (InterruptedException e) {}

                broadcast("⏳ Timpul a expirat!");

                for (Player p : players) {
                    if (p.getCurrentAnswer() != null && p.getCurrentAnswer().equalsIgnoreCase(correctAnswer)) {
                        p.addPoints(System.currentTimeMillis() - questionStartTime);
                    }
                }
            }
            isRunning = false;
            announceWinner();
        }).start();
    }

    private void announceWinner() {
        players.sort((p1, p2) -> {
            if (p1.getScore() != p2.getScore()) return Integer.compare(p2.getScore(), p1.getScore());
            return Long.compare(p1.getTotalTime(), p2.getTotalTime());
        });

        broadcast("\n=== CLASAMENT FINAL ===");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            broadcast((i + 1) + ". " + p.getName() + " | Scor: " + p.getScore() + " | Timp total: " + (p.getTotalTime() / 1000.0) + "s");
        }

        saveGameToDatabase();
    }

    private void saveGameToDatabase() {
        try {
            GameSessionRepository gameRepo = new GameSessionRepository();
            PlayerProfileRepository playerRepo = new PlayerProfileRepository();
            ResultRepository resultRepo = new ResultRepository();

            GameSession currentSession = new GameSession();
            gameRepo.create(currentSession);

            for (Player p : players) {
                PlayerProfile profile = playerRepo.findByName(p.getName());

                if (profile == null) {
                    profile = new PlayerProfile(p.getName());
                    playerRepo.create(profile);
                }

                Result finalResult = new Result(currentSession, profile, p.getScore(), p.getTotalTime());
                resultRepo.create(finalResult);
            }
            System.out.println("[DB] Meciul și rezultatele au fost salvate cu succes!");
        } catch (Exception e) {
            System.err.println("[DB] Eroare la salvarea în baza de date: " + e.getMessage());
        }
    }
}