import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieControler {
    @GetMapping
    public List<Movie> getAllMovies()
    {
        try (Connection con=DatabaseConnection.getConnection()) {
            MovieDAO movieDAO=new MovieDAO(con);
            return movieDAO.findListOfMovies();
        } catch (SQLException e) {
            System.err.println("Eroare la extragerea filmelor: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
