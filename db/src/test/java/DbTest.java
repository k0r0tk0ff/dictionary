import org.apache.log4j.Logger;
import org.junit.Test;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DbTest {
    private static final Logger log = Logger.getLogger(DbTest.class.getName());

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

        SQLException dataAccessError = null;
        SQLException closeConnectionError = null;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/dictionary",
                "postgres",
                "zxcvbnm");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            dataAccessError = e;
        } finally {
            if (connection != null) {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                }
                catch (SQLException c) {
                    closeConnectionError = c;
                }
            } else {
                System.out.println(" Connection Failed !");
            }
        }

       // handleExceptions(dataAccessError, closeConnectionError);
    }

    @Test
    public void testLogWrite() {


        log.debug(" Error handling enabled ");
        log.error(" Test Error-facility message ");
        log.info(" Test Error-facility message ");

        /*log.debug("Start method testLogWrite()");
        if (log.isDebugEnabled()) {
            log.debug(" Error handling enabled ");
            log.error(" Test Error-facility message ");
            log.info(" Test Error-facility message ");
        }
        log.debug("done");*/
    }

/*    private void handleExceptions(Exception e1, Exception e2) {
        log.debug("Start processing");
        if (log.isDebugEnabled()) {
            log.debug(" Error handling enabled ");
        }
        try {
            log.debug("Result: " + e1);
            log.debug("Result: " + e2);
        } catch (Exception e) {
            log.error("Something failed", e);
        }
        log.debug("done");
    }*/
}



