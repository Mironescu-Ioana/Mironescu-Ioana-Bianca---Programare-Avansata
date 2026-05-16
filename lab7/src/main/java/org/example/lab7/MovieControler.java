package org.example.lab7;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies", description = "Operatii pentru baza de date cu filme")
public class MovieControler {

    @GetMapping
    @Operation(summary = "Returneaza toate filmele din baza de date")
    public ResponseEntity<List<Movie>> getAllMovies() throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            MovieDAO movieDAO = new MovieDAO(con);
            return ResponseEntity.ok(movieDAO.findListOfMovies());
        }
    }

    @PostMapping
    @Operation(summary = "Adauga un film nou")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            MovieDAO movieDAO = new MovieDAO(con);
            movieDAO.create(movie);
            con.commit();
            return ResponseEntity.status(HttpStatus.CREATED).body("Filmul a fost adaugat cu succes.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica toate detaliile unui film")
    public ResponseEntity<String> updateMovie(@PathVariable int id, @RequestBody Movie movie) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            MovieDAO movieDAO = new MovieDAO(con);
            movieDAO.update(id, movie);
            con.commit();
            return ResponseEntity.ok("Filmul a fost actualizat cu succes.");
        }
    }

    @PatchMapping("/{id}/score")
    @Operation(summary = "Schimba doar nota unui film")
    public ResponseEntity<String> updateMovieScore(@PathVariable int id, @RequestBody Map<String, Double> payload) throws SQLException {
        if (!payload.containsKey("score")) {
            throw new IllegalArgumentException("Trebuie sa trimiti campul 'score' (nota).");
        }
        try (Connection con = DatabaseConnection.getConnection()) {
            MovieDAO movieDAO = new MovieDAO(con);
            movieDAO.updateScore(id, payload.get("score"));
            con.commit();
            return ResponseEntity.ok("Nota filmului a fost modificata.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Sterge un film dupa ID")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            MovieDAO movieDAO = new MovieDAO(con);
            movieDAO.delete(id);
            con.commit();
            return ResponseEntity.ok("Filmul a fost sters din baza de date.");
        }
    }
}

