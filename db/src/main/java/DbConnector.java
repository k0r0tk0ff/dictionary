import java.io.*;
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

    public static DbConnector getInstance() {return INSTANCE;}

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}

