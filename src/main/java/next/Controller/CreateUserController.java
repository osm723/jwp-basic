package next.Controller;

import core.mvc.Controller;
import next.model.User;
import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        try {
            userDao.insert(user);
            return "redirect:/";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error.jsp";
        }
    }
}
