import java.sql.*;

public class GenreDAO {
    private Connection con;

    public GenreDAO(Connection con) {
        this.con = con;
    }

    public void create(String name) throws SQLException {
        String sql="INSERT INTO genres (name) VALUES(?)";

        try (PreparedStatement pstmt=con.prepareStatement(sql))
        {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public Integer findByName(String name) throws SQLException
    {
        String sql="SELECT id FROM genres WHERE name= ?";

        try (PreparedStatement pstmt=con.prepareStatement(sql)){
            pstmt.setString(1, name);
            try(ResultSet rs=pstmt.executeQuery()) {
                if(rs.next())
                    return rs.getInt("id");
            }
        }
        return null;
    }

    public String findById(int id) throws SQLException
    {
        String sql="SELECT name FROM genres WHERE id = ?";

        try (PreparedStatement pstmt= con.prepareStatement(sql))
        {
            pstmt.setInt(1, id);
            try(ResultSet rs=pstmt.executeQuery())
            {
                if(rs.next())
                    return rs.getString("name");
            }
        }
        return null;
    }
}
