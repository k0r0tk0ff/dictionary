
package servlets;

//import businesslogic.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import org.apache.catalina.startup.Tomcat;

//TODO: не совсем понятно, почему запускающий класс находится в модуле web
public class Main {

    private static final Logger LOG  = LoggerFactory.getLogger(Main.class);

    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

    public static void main(String[] args) throws Exception {

        String contextPath = "/" ;
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(PORT.orElse("8080") ));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);
        tomcat.start();
        tomcat.getServer().await();
    }
}
