package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class Dispatcherservlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(Dispatcherservlet.class);

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private RequestMapping requestMapping;

    @Override
    public void init() {
        log.info("=========================init=========================");
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String requestUri = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        Controller controller = requestMapping.getController(requestUri);
        try {
            String viewName = controller.execute(request, response);
            returnPage(viewName, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void returnPage(String viewName, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }
}
