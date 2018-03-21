package dbconnector;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DbConnector {

    private static volatile DbConnector INSTANCE;

    private static Properties properties;
    private InputStream input = null;

    private DbConnector() throws Exception{
        properties = new Properties();
        input = getClass().getResourceAsStream("/dbConnect.properties" );
        properties.load(input);
        input.close();

/*        try {
            input = this.getClass().getClassLoader().getResourceAsStream("dbConnect.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    //Use double check and volatile, see
    //https://habrahabr.ru/post/129494/
    public static DbConnector getInstance() throws Exception{
        DbConnector localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (DbConnector.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new DbConnector();
                }
            }
        }

        return localInstance;
    }

    public Connection connection = null;

    public void initializeConnection() throws SQLException {
        connection = DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.user"),
                properties.getProperty("jdbc.password")
                );
        createDb(connection);
    }

    public void closeConnection (Connection connection) throws SQLException {
        connection.close();
    }

    public void createDb(Connection connection) throws SQLException {
        Statement statement = null;

        statement = connection.createStatement();
        String createDbTable = "CREATE TABLE IF NOT EXISTS DICTIONARY(en VARCHAR(50) PRIMARY KEY, ru VARCHAR(50)); ";

        statement.execute(createDbTable);
        statement.close();
    }

    public String doGetTranslatedWordFromDb (String wordForTranslate, String resolvedLn)
            throws Exception {
        String result = "";

        String sql = String.format("SELECT * FROM DICTIONARY WHERE %s = '%s';", resolvedLn, wordForTranslate);

        PreparedStatement pr = getConnection().prepareStatement(sql);
        ResultSet resultSet = pr.executeQuery();
        while (resultSet.next()) {
            String english = resultSet.getString("en");
            String russian = resultSet.getString("ru");

            result = String.format("%s = %s", english, russian);
        }

        pr.close();

        return result;
    }

    public void doInsertToDbResult( String wordForTranslate, String resolvedLn, String transWord) throws SQLException {
        String reverse;

        reverse = (resolvedLn.equals("ru")) ? "en" : "ru";

        String sqlFlag = "";
        //--------------------------------------
        switch (resolvedLn) {
            case "ru": sqlFlag = "SELECT * FROM DICTIONARY WHERE ru = ?"; break;
            case "en": sqlFlag = "SELECT * FROM DICTIONARY WHERE en = ?"; break;
        }

        //--------------------------------------

        //String sqlFlag = String.format("SELECT * FROM DICTIONARY WHERE %s = '%s'", resolvedLn, wordForTranslate);
        PreparedStatement pr1 = getConnection().prepareStatement(sqlFlag);
        pr1.setString(1, wordForTranslate);
        ResultSet resultSet = pr1.executeQuery();

        if(!resultSet.next()) {
        String sql = String.format("INSERT INTO DICTIONARY (%s, %s) VALUES (?, ?);",resolvedLn, reverse);

        //do not work! sql error!
        //String sql = String.format(
        //        "BEGIN IF NOT EXISTS (SELECT * FROM DICTIONARY WHERE %s = '%s') BEGIN INSERT INTO DICTIONARY (%s, %s) VALUES (?, ?) END END ",resolvedLn, wordForTranslate,resolvedLn, reverse);
        PreparedStatement pr2 = getConnection().prepareStatement(sql);
        pr2.setString(1, wordForTranslate);
        pr2.setString(2, transWord);

        //pr.executeUpdate();
        pr2.execute();
        pr2.close();
        }

        resultSet.close();
        pr1.close();
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public Connection getConnection (){
        return this.connection;
    }
}

