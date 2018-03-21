package servlets;

import businesslogic.Translator;
import businesslogic.yandex.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

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
        String result = "";

        request.setCharacterEncoding("UTF-8");

        try {
            translatorApi = YandexApiExe.getInstance();

            if (request.getParameter("UseProxy") != null) {
                result = translatorApi.doGetTranslatedWord
                        (request.getParameter("wordForTranslate"), true);
            } else {
                result = translatorApi.doGetTranslatedWord
                        (request.getParameter("wordForTranslate"), false);
            }
        } catch (NoSuchFileException e) {
            LOG.error("Check exist file \"yandexapi.properties\" or \"proxy.properties\" with correct key in directory with executable jar file.");
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        response.setCharacterEncoding("UTF-8");

        if (!result.equals("")) {
            request.setAttribute("result", result);
        } else {
            request.setAttribute("result", "Error! See log!");
            System.exit(1);
        }

        request.getRequestDispatcher("index.jsp").include(request, response);
    }
}