import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection con=DatabaseConnection.getConnection()) {
            GenreDAO genres = new GenreDAO(con);
            MovieDAO movieDAO = new MovieDAO(con);

            //inseram genul
            String numeGen = "Crime";
            try {
                genres.create(numeGen);
            } catch (SQLException e) {
                System.out.println("Genul exista deja, mergem mai departe.");
            }

            //aflam ID-ul generat
            Integer genreIdCrime = genres.findByName(numeGen);
            System.out.println("ID-ul gasit pentru " + numeGen + " este: " + genreIdCrime);

            //inseram filmul cu ID-ul corect
            if (genreIdCrime != null) {
                Movie filmNou = new Movie(null, "The Godfather", Date.valueOf("1972-03-24"), 175, 9.2, genreIdCrime);
                movieDAO.create(filmNou);
                System.out.println("Filmul a fost inserat cu succes!");
            }

            con.commit();
            System.out.println("Tranzactia a fost finalizata cu succes!");

            ReportGenerator.generateHTMLReport();
        } catch (SQLException e) {
            System.err.println("Eroare de la baza de date: " + e.getMessage());
        } finally {
            DatabaseConnection.closePool();
        }
    }
}
