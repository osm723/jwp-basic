package next.Controller;

import next.model.User;

import javax.servlet.http.HttpSession;
import java.util.UUID;

public class UserSessionUtils {


    public static final String USER_SESSION_KEY = "user";

    public static boolean isSameUser(HttpSession session, User user) {
        User userSession = (User) session.getAttribute("user");
        if (userSession == null) {
            return false;
        }

        return userSession.equals(user);
    }
}
