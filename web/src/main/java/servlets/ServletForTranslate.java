package servlets;

import businesslogic.Translator;
import businesslogic.yandex.YandexApiExe;

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

        Translator translatorApi;
        String result = null;
        request.setCharacterEncoding("UTF-8");

        try {
            translatorApi = YandexApiExe.getInstance();
                result = translatorApi.doGetTranslatedWord(request.getParameter("wordForTranslate"));
            } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            }

        response.setCharacterEncoding("UTF-8");

        if (result != null) {
        request.setAttribute("result", result);
        } else {
            request.setAttribute("result", "Error! See log!");
            System.exit(1);
        }

        request.getRequestDispatcher("index.jsp").include(request, response);
    }
}