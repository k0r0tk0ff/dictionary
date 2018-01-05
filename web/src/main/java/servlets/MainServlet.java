package servlets;

import businesslogic.YandexApiExe;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(MainServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        YandexApiExe yaApi = YandexApiExe.getInstance();

        request.setCharacterEncoding("UTF-8");

        String result = null;
        try {
            result = yaApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        response.setCharacterEncoding("UTF-8");

        request.setAttribute("result", result);

        request.getRequestDispatcher("index.jsp").include(request, response);

/*        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request, response);*/

        //out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // request.setCharacterEncoding("UTF-8");
        System.out.println(String.format("(do get) request.getCharacterEncoding() = %s",request.getCharacterEncoding()));
    }

}