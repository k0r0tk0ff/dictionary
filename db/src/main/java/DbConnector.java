import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbConnector {

    private static final DbConnector INSTANCE = new DbConnector();
    private static Properties properties;
    private InputStream input = null;

    private DbConnector() {
        properties = new Properties();
        input = this.getClass().getClassLoader().getResourceAsStream("dbConnect.properties");
        try {
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
        }
    }

    public Connection connection = null;

    public void initializeConnection() throws SQLException {
/*        connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password")
                );*/

        connection = DriverManager.getConnection(
                "jdbc:h2:~/test",
                "sa",
                ""
                );
    }

    public void closeConnection (Connection connection) throws SQLException {
        connection.close();
    }

    public void createDb(Connection connection) throws SQLException {
        Statement statement = null;

        statement = connection.createStatement();
        String createDbTable = "CREATE TABLE IF NOT EXISTS DICTIONARY(ENG VARCHAR(50) PRIMARY KEY, RU VARCHAR(50)); ";
        //String fillTable = "INSERT INTO DICTIONARY VALUES('cat', 'кошка')";

        statement.execute(createDbTable);
        //statement.execute(fillTable);
        statement.close();
    }



    public static DbConnector getInstance() {
        return INSTANCE;
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public Connection getConnection (){
        return this.connection;
    }
}

