import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DbTest {

   //Logger log  = LoggerFactory.getLogger(DbTest.class);

    @Test
    public void loadDriverTestH2() throws Exception {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Install H2 JDBC Driver!");
            e.printStackTrace();
            return;
        }
        System.out.println("H2 JDBC Driver Registered.");
    }
     /*
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
    }*/

    @Test
    public void loadPropertiesFromFile() throws Exception {

        DbConnector connector = DbConnector.getInstance();
        assertThat(connector.getPropertyValue("jdbc.username"), is("sa"));
        System.out.println("Read properties from file success.");
    }

    /*
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

        log.debug("Debug message (do not use isDebugEnabled)");
        log.error("Error message (do not use isDebugEnabled)");
        log.info("Info message (do not use isDebugEnabled)");
        try {
            if (log.isDebugEnabled()) {
                log.debug("Start method testLogWrite()");
                log.debug(" Error handling enabled ");
                log.error(" Test Error-facility message");
                log.info(" Test Info-facility message ");
                log.debug("End method testLogWrite()");
                System.out.println("Log write success. (see db.log)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleExceptions(SQLException dataAccessError, SQLException closeConnectionError) {

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
    }*/

    @Test
    public void createDb() {
        String sql = "SELECT * FROM DICTIONARY;";
        DbConnector connector = DbConnector.getInstance();

        try {
            connector.initializeConnection();
            connector.createDb(connector.getConnection());

            PreparedStatement pr = connector.getConnection().prepareStatement(sql);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                String eng = resultSet.getString("ENG");
                String ru = resultSet.getString("RU");
                System.out.println(String.format("%s | %s", eng, ru));
            }

            System.out.println(resultSet.toString());

            connector.closeConnection(connector.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



