import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGenerator {

    public static void generateHTMLReport() {
        String templateStart = """
                <!DOCTYPE html>
                <html>
                <head><title>Raport Filme</title>
                <style>
                    table { font-family: Arial, sans-serif; border-collapse: collapse; width: 100%; }
                    th { background-color: #4CAF50; color: white; padding: 12px; }
                    td { border: 1px solid #ddd; padding: 8px; }
                    tr:nth-child(even) { background-color: #f2f2f2; }
                </style>
                </head>
                <body>
                <h2>Catalogul Meu de Filme</h2>
                <table>
                  <tr><th>Titlu</th><th>Data Lansare</th><th>Nota</th><th>Gen</th></tr>
                """;

        String templateEnd = """
                </table>
                </body>
                </html>
                """;

        StringBuilder tableRows=new StringBuilder();

        //ma conectez la DB si citesc din view
        try (Connection con=DatabaseConnection.getConnection();
             PreparedStatement pstmt=con.prepareStatement("SELECT * FROM movie_report_view");
             ResultSet rs=pstmt.executeQuery()) {

            while (rs.next()) {
                tableRows.append("<tr>")
                        .append("<td>").append(rs.getString("nume_film")).append("</td>")
                        .append("<td>").append(rs.getDate("data_lansare")).append("</td>")
                        .append("<td>").append(rs.getDouble("nota")).append("</td>")
                        .append("<td>").append(rs.getString("nume_gen")).append("</td>")
                        .append("</tr>\n");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea din view: "+e.getMessage());
        }

        String finalHtml=templateStart+tableRows.toString()+templateEnd;

        try(FileWriter fileWriter=new FileWriter("raport_file.html")) {
            fileWriter.write(finalHtml);
            System.out.println("Raportul HTML a fost generat cu succes infolderul proiectului!");
        } catch (IOException e) {
            System.err.println("Eroare la popularea fisierului: "+e.getMessage());
        }

    }
}
