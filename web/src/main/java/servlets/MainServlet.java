package servlets;

import businesslogic.YandexApiExe;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(MainServlet.class.getName());


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.setLevel(Level.DEBUG);

        YandexApiExe yaApi = null;
        String result = null;
        request.setCharacterEncoding("UTF-8");

            try {
                //yaApi = YandexApiExe.getInstance();
                //result = yaApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
                throw new Exception("afffff");
            } catch (Exception e) {
                System.out.println("Intercept!!!");
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