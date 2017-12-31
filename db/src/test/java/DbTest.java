import org.apache.log4j.Level;
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

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Install PostgreSQL JDBC Driver!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered.");
    }

    @Test
    public void loadPropertiesFromFile() throws Exception {

        /*
         *  Test load settings from file
         *  (with private constructor)
         */
        DbConnector connector = DbConnector.getInstance();
        assertThat(connector.getValue("jdbc.username"), is("postgres"));
        System.out.println("Read properties from file success.");
    }

    @Test
    public void testConnectToDb() throws SQLException {

        SQLException dataAccessError = null;
        SQLException closeConnectionError = null;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/dictionary",
                "postgres",
                "zxcvbnm");
        } catch (SQLException e1) {
            dataAccessError = e1;
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

        if(dataAccessError != null || closeConnectionError != null) {
            handleExceptions(dataAccessError, closeConnectionError);
        } else {
            System.out.println("DB Connection success.");
        }
    }

    @Test
    public void testLogWrite() {

        log.setLevel(Level.DEBUG);

        try {
            if (log.isDebugEnabled()) {
                log.debug("Start method testLogWrite()");
                log.debug(" Error handling enabled ");
                log.error(" Test Error-facility message");
                log.info(" Test Info-facility message ");
                log.debug("End method testLogWrite()");
                System.out.println("Log write success. (see my.log)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleExceptions(SQLException dataAccessError, SQLException closeConnectionError) {

        log.setLevel(Level.DEBUG);

        log.debug("Start processing");
        if (log.isDebugEnabled()) {
            log.debug(" Error handling enabled ");
        }
        try {
            log.debug("dataAccessError: " + dataAccessError);
            log.debug("closeConnectionError: " + closeConnectionError);
        } catch (Exception e) {
            log.error("Something failed", e);
        }
        log.debug("done");
    }
}



