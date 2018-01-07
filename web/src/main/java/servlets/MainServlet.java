package servlets;

import businesslogic.YandexApiExe;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    //private static final Logger log = Logger.getLogger(MainServlet.class.getName());
    private Logger log;

    public void init() {
        this.log = Logger.getLogger(MainServlet.class);

/*        String realPath = getServletContext().getRealPath("/");
        String fileSep = System.getProperty("file.separator");

        if (realPath != null && (!realPath.endsWith(fileSep)))
            realPath = realPath + fileSep;

        System.out.println(realPath);

        //load the configuration for this application's loggers using the
        // servletLog.properties file
        PropertyConfigurator.configure(realPath
                + "WEB-INF/classes/servletLog.properties");
        //create the logger for this servlet class
        //it will use the configuration for the logger com.java2s.LoggerServlet
        //or inherit from the logger com.java2s if one exists, and so on
        log = Logger.getLogger(MainServlet.class);
        log.info("LoggerServlet started.");*/

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.setLevel(Level.DEBUG);

        YandexApiExe yaApi = null;
        String result = null;
        request.setCharacterEncoding("UTF-8");

        log.debug(" DEBUG Test MainServlet debug before error");
        log.info("Sending an INFO message");
        log.fatal("Sending an fatal message");


        try {
                //yaApi = YandexApiExe.getInstance();
                //result = yaApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
                throw new Exception("afffff");
            } catch (Exception e) {
                log.error(e.getMessage());
                log.debug(" Test MainServlet debug");
            }

        response.setCharacterEncoding("UTF-8");
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").include(request, response);
    }

/*
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // request.setCharacterEncoding("UTF-8");
        System.out.println(String.format("(do get) request.getCharacterEncoding() = %s",request.getCharacterEncoding()));
    }
*/

}