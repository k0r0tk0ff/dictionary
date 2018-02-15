package exe;

import java.io.File;
import java.util.Optional;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import servlets.ServletForTranslate;

public class Exe {

   // private static final Logger LOG  = LoggerFactory.getLogger(Exe.class);

    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

    public static void main(String[] args) throws Exception {

        /*String contextPath = "/" ;
        String appBase = ".";*/
        Tomcat tomcat = new Tomcat();
        /*tomcat.setPort(Integer.valueOf(PORT.orElse("8080") ));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);*/

        //------------------------------------------

        Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "ServletForTranslate", new ServletForTranslate());
        ctx.addServletMapping("/*", "ServletForTranslate");
        //ctx.addServletMapping("/ServletForTranslate", "ServletForTranslate");
        tomcat.start();
        //------------------------------------------

        tomcat.getServer().await();
    }
}