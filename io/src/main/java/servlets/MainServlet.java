package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Original - http://www.javatpoint.com/servlet-http-session-login-and-logout-example
 */

public class MainServlet extends HttpServlet {

    /**
     * @param request  - standard http request
     * @param response - standard http response
     * @throws ServletException - standard ServletException
     * @throws IOException - standard IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String result = "asdf";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").include(request, response);

        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.write("asdf");
    }
}