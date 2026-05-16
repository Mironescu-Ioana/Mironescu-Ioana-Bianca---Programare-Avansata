package org.example.lab7;
import java.sql.*;

public class ActorDAO {
    //inseram un obiect in DB
    public void create(Actor actor) throws SQLException {
        Connection connect = DatabaseConnection.getConnection();
        String sql = "INSERT INTO actors (name) VALUES (?)";

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, actor.getName());

            pstmt.executeUpdate();
        }
    }

    public Actor findById(int id) throws SQLException {
        Connection connect = DatabaseConnection.getConnection();
        String sql="SELECT * FROM actors WHERE id = ?";

        try(PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs=pstmt.executeQuery()) {
                if(rs.next())
                    return new Actor(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }
}

