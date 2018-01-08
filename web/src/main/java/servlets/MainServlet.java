package servlets;

import businesslogic.YandexApiExe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    Logger log  = LoggerFactory.getLogger(MainServlet.class);

/*
    public void init() {
        this.log = Logger.getLogger(MainServlet.class);
    }
*/


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        YandexApiExe yaApi = null;
        String result = null;
        request.setCharacterEncoding("UTF-8");

        log.debug(" DEBUG Test MainServlet debug before error");
        log.info("Sending an INFO message");
        log.error("Sending an fatal message");


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