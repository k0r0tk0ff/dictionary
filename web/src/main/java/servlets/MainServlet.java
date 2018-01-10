package servlets;

import businesslogic.YandexApiExe;
import org.apache.logging.log4j.web.WebLoggerContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    Logger log = null;

/*    public void init() {
        this.log = LoggerFactory.getLogger(MainServlet.class);
    }*/

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log = LoggerFactory.getLogger(MainServlet.class);
        log.debug(" DEBUG Test MainServlet from service");
        log.info("Sending an INFO message from service");
        log.error("Sending an fatal message from service");
        super.service(req,resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Logger log = LoggerFactory.getLogger(MainServlet.class);

        YandexApiExe yaApi = null;
        String result = null;
        request.setCharacterEncoding("UTF-8");

        log.debug(" DEBUG Test MainServlet debug before error");
        log.info("Sending an INFO message");
        log.error("Sending an fatal message");

        try {
                log.info("Sending an INFO message from try block");
                yaApi = YandexApiExe.getInstance();
                result = yaApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
                //throw new Exception("afffff");
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