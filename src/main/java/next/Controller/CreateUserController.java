package next.Controller;

import next.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/users/create", "/users/form"})
public class CreateUserController extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/form.jsp");
        requestDispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("CreateUserController=========================" + request.getParameter("userId"));
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        log.info("addUser=getUserId=======================" + user.getUserId());
        log.info("addUser=toString=======================" + user.toString());
        DataBase.addUser(user);
        response.sendRedirect("/");
    }
}
