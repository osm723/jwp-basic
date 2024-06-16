package next.Controller;

import core.mvc.Controller;
import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDao userDao = new UserDao();
        try {
            request.setAttribute("users", userDao.findAll());
            return "home.jsp";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error.jsp";
        }
    }
}
