package models;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameSession game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerProfile player;

    @Column(nullable = false)
    private int score;

    @Column(name = "total_time_ms", nullable = false)
    private long totalTimeMs;

    public Result() {}

    public Result(GameSession game, PlayerProfile player, int score, long totalTimeMs) {
        this.game = game;
        this.player = player;
        this.score = score;
        this.totalTimeMs = totalTimeMs;
    }

    public Long getId() { return id; }
    public GameSession getGame() { return game; }
    public PlayerProfile getPlayer() { return player; }
    public int getScore() { return score; }
    public long getTotalTimeMs() { return totalTimeMs; }
}