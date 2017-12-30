import org.junit.Test;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DbTest {
    @Test
    public void loadDriverTest() throws Exception {

        /*
         *  Check installed JDBC driver
         */
        System.out.println("--- PostgreSQL JDBC Connection Testing ----");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Install PostgreSQL JDBC Driver!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
    }

    @Test
    public void loadPropertiesFromFile() throws Exception {

        /*
         *  Test load settings from file
         *  (with private constructor)
         */
        DbConnector connector = DbConnector.getInstance();
        assertThat(connector.getValue("jdbc.username"), is("postgres"));
    }

    @Test
    public void testConnectToDb() throws SQLException {

        /*
         *  Test connection
         */
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/dictionary",
                "postgres",
                "zxcvbnm");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                System.out.println(" Connect successful !");
                connection.close();
            } else {
                System.out.println(" Connection Failed !");
            }
        }
    }
}

