package dbconnector;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public Connection getConnection (){
        return this.connection;
    }
}

