package org.example.lab7;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private Connection con;

    public MovieDAO(Connection con) {
        this.con = con;
    }

    //inseram un obiect in DB
    public void create(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, release_date, duration, score, genre_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setDate(2, movie.getReleaseDate());
            pstmt.setInt(3, movie.getDuration());
            pstmt.setDouble(4, movie.getScore());
            pstmt.setInt(5, movie.getGenreId());

            pstmt.executeUpdate();
        }
    }

    public void update(int id, Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title=?, release_date=?, duration=?, score=?, genre_id=? WHERE id=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setDate(2, movie.getReleaseDate());
            pstmt.setInt(3, movie.getDuration());
            pstmt.setDouble(4, movie.getScore());
            pstmt.setInt(5, movie.getGenreId());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
    }

    public void updateScore(int id, Double score) throws SQLException {
        String sql = "UPDATE movies SET score=? WHERE id=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDouble(1, score);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Movie findById(int id) throws SQLException {
        String sql="SELECT * FROM movies WHERE id = ?";

        try(PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs=pstmt.executeQuery()) {
                if(rs.next())
                    return new Movie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDate("release_date"),
                            rs.getInt("duration"),
                            rs.getDouble("score"),
                            rs.getInt("genre_id")
                    );
            }
        }
        return null;
    }

    public List<Movie> findListOfMovies() throws SQLException
    {
        List<Movie> movies=new ArrayList<>();
        String sql="SELECT * FROM movies";

        try (PreparedStatement pstmt=con.prepareStatement(sql);
             ResultSet rs=pstmt.executeQuery()) {
            while(rs.next())
            {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("release_date"),
                        rs.getInt("duration"),
                        rs.getDouble("score"),
                        rs.getInt("genre_id")
                ));
            }
        }
        return movies;
    }
}

