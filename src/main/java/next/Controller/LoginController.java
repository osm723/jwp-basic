package next.Controller;

import core.mvc.Controller;
import next.model.User;
import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        try {
            User user = userDao.findByUserId(userId);

            if (user == null) {
                request.setAttribute("loginFailed", true);
                return "/user/login.jsp";
            }
            if (user.matchPassword(password)) {
                HttpSession session = request.getSession();
                session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
                return "redirect:/";
            } else {
                request.setAttribute("loginFailed", true);
                return "/user/login.jsp";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error.jsp";
        }
    }
}
