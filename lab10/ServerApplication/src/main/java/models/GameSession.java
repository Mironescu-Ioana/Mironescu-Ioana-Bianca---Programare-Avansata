package models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;

    public GameSession() {
        this.playedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public LocalDateTime getPlayedAt() { return playedAt; }
}