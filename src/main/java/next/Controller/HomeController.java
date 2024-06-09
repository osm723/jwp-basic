package next.Controller;

import next.db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("home======================");

        req.setAttribute("users", DataBase.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        //RequestDispatcher rd = req.getRequestDispatcher("index2.html");
        requestDispatcher.forward(req, resp);
    }
}
