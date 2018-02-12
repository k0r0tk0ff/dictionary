import dbconnector.DbConnector;
import org.junit.Test;

import java.sql.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DbTest {

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

    @Test
    public void loadPropertiesFromFile() throws Exception {
        DbConnector connector = DbConnector.getInstance();
        assertThat(connector.getPropertyValue("jdbc.user"), is("sa"));
        System.out.println("Read properties from file success.");
    }

    @Test
    public void showDataFromDb() {
        String sql = "SELECT * FROM DICTIONARY;";
        DbConnector connector = DbConnector.getInstance();

        try {
            connector.initializeConnection();
            connector.createDb(connector.getConnection());

            PreparedStatement pr = connector.getConnection().prepareStatement(sql);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                String eng = resultSet.getString("en");
                String ru = resultSet.getString("ru");
                System.out.println(String.format("%s | %s", eng, ru));

            }


            connector.closeConnection(connector.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void doInsertToDb() throws SQLException {

        DbConnector connector = DbConnector.getInstance();
        try {

            connector.initializeConnection();
            connector.createDb(connector.getConnection());

            String sql = String.format("INSERT INTO DICTIONARY (en, ru) VALUES ('cat', 'кошка');");
            Statement st = connector.getConnection().createStatement();
            st.execute(sql);
            st.close();

            }  catch (SQLException e) {
            e.printStackTrace();
        }
        connector.closeConnection(connector.getConnection());

    }

        /*
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
}



