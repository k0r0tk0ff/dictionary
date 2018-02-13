
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
        name = "MainServlet",
        urlPatterns = {"/MainServlet"}
)

//TODO: название сервлета не точно описывает суть производимых действий
public class MainServlet extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(MainServlet.class);

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
                //TODO: важно логировать весь stacktrace, так как сообщения мжет вообще не быть или оно не информативно
            LOG.error(e.getMessage());
                //TODO: немного неверное использование лога,  debug здесь не нужен, так как логируется ошибка
            LOG.debug(" Catch exception in try block");
            }

        response.setCharacterEncoding("UTF-8");
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").include(request, response);
    }
}
