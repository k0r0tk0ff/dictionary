package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String result = request.getParameter("wordForTranslate");

        //----------------
/*        String uri = "asdf";
        HttpRequest<String> httpRequest = HttpRequestBuilder.createGet(uri, String.class)
                .responseDeserializer(ResponseDeserializer.ignorableDeserializer()).build();

        public void send(){
            ResponseHandler<String> response = httpRequest.execute(someParamsYouWant);
            System.out.println(response.getStatusCode());
            System.out.println(response.get()); //retuns response body
        }*/

        //----------------



        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").include(request, response);

        out.close();
    }
}