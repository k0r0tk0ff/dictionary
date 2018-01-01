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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("link.html").include(request, response);

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (password.equals("a")) {
            out.print(String.format("<center><h2> Welcome, %s </center></h2>", name));
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            out.print(String.format("<center><h2> SessionID = %s </center></h2>", session.getId()));
        } else {
            out.print("<center><h2> Sorry, username or password error! </h2></center>");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.write("asdf");
    }
}