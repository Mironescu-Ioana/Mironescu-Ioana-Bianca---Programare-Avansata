import java.io.PrintWriter;

public class Player {
    private String name;
    private int score = 0;
    private long totalTime = 0;
    private String currentAnswer = null;
    private long lastAnswerTime = 0;
    private PrintWriter out;

    public Player(String name, PrintWriter out) {
        this.name = name;
        this.out = out;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public long getTotalTime() { return totalTime; }
    public String getCurrentAnswer() { return currentAnswer; }

    public void setCurrentAnswer(String answer, long timeTaken) {
        this.currentAnswer = answer;
        this.lastAnswerTime = timeTaken;
    }

    public void addPoints(long timeTaken) {
        this.score++;
        this.totalTime += timeTaken;
    }

    public void resetForNewQuestion() {
        this.currentAnswer = null;
        this.lastAnswerTime = 0;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }
}