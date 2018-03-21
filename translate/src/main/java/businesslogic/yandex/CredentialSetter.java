package businesslogic.yandex;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class CredentialSetter {

    private static volatile CredentialSetter instance;

    public static String host;
    public static String port;
    String userName;
    String password;

    private static Properties properties;

    public static CredentialSetter getInstance() throws Exception{
        CredentialSetter localInstance = instance;
        if (localInstance == null) {
            synchronized (CredentialSetter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CredentialSetter();
                }
            }
        }
        return localInstance;
    }

    private void getCredentialsFromFileAndSet() throws IOException {
        properties = new Properties();
        Path path = Paths.get("proxy.properties");
        BufferedReader input = Files.newBufferedReader(path, Charset.forName("UTF-8"));
        properties.load(input);
        input.close();
    }

    private CredentialSetter() throws IOException {
        getCredentialsFromFileAndSet();

        this.host = properties.getProperty("host");
        this.port = properties.getProperty("port");
        this.userName = properties.getProperty("userName");
        this.password = properties.getProperty("password");
    }

    public CredentialsProvider getCredentials() {
        CredentialsProvider credentials;

        credentials = new BasicCredentialsProvider();

        credentials.setCredentials(
                new AuthScope(this.host, Integer.parseInt(this.port)),
                new UsernamePasswordCredentials(this.userName, this.password)
        );
        return credentials;
    }
}
