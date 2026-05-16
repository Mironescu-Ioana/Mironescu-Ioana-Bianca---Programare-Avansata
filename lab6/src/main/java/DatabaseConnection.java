import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //pool-ul de conexiuni cu DB-ul
    public static final HikariDataSource dataSource;

    static {
        HikariConfig config=new HikariConfig();
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
        config.setUsername("student");
        config.setPassword("STUDENT");

        config.setMaximumPoolSize(10);
        config.setAutoCommit(false);

        //Se initializeaza pool-ul cu setarile config
        dataSource=new HikariDataSource(config);
    }

    private DatabaseConnection() {};

    //obtin o conexiune libera din pool
    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    //se inchide pool-ul la final
    public static void closePool()
    {
        if(dataSource!=null)
        {
            dataSource.close();
        }
    }
}
