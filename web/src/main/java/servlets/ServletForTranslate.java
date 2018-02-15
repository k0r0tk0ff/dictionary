package servlets;

import businesslogic.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "ServletForTranslate",
        urlPatterns = {"/ServletForTranslate"}
)

public class ServletForTranslate extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(ServletForTranslate.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        YandexApiExe yaApi;
        String result = null;
        request.setCharacterEncoding("UTF-8");

        try {
            LOG.info("Sending an INFO message from try block");
                yaApi = YandexApiExe.getInstance();
                result = yaApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
            } catch (Exception e) {
            LOG.error(e.toString());
            }

        response.setCharacterEncoding("UTF-8");
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").include(request, response);
    }
}