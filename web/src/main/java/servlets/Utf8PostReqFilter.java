package servlets;

import javax.servlet.*;
import java.io.IOException;

/**
 * see
 * http://www.skipy.ru/technics/encodings_webapp.html
 */
public class Utf8PostReqFilter implements Filter{

    private static final String FILTERABLE_CONTENT_TYPE="application/x-www-form-urlencoded";

    private static final String ENCODING_DEFAULT = "UTF-8";

    private static final String ENCODING_INIT_PARAM_NAME = "encoding";

    private String encoding;

    public void destroy(){
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        String contentType = req.getContentType();



        System.out.println(String.format("contentType = %s", contentType));


        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE))
            req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);

        String contentTypeForGet = req.getCharacterEncoding();

        System.out.println("Filter work");
        System.out.println(req.getCharacterEncoding());
        System.out.println(String.format("CharacterEncoding = %s", contentTypeForGet));
    }

    public void init(FilterConfig config) throws ServletException{
        encoding = config.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
    }
}
