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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users")
public class ListUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("ListUserController============");
        request.setAttribute("users", DataBase.findAll());

        List<User> userList = DataBase.findAll().stream().collect(Collectors.toList());
        log.info("ListUserController=size==========" + userList.size());
        log.info("ListUserController=get==========" + userList.get(0));
        log.info("ListUserController=users==========" + request.getAttribute("users"));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/list.jsp");
        requestDispatcher.forward(request, response);
    }

}
