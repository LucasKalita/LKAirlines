package example.user;


import example.json.UserJsonController;

import java.io.IOException;

public class LoginController {
    private static final UserJsonController users = new UserJsonController();

    public static User login(String email, String password) {
        if(email.equals("admin@acs") && password.equals("admin")) {
            return new Admin();
        }
        try {
            return users.loginUser(email, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static User logout() {
        return new Quest();
    }
}
