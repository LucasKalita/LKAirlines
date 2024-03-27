package example.user;



import example.json.UserJsonController;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterController {
    private static final UserJsonController userJsonController = new UserJsonController();

    public static void register(String email, String password) {
        try {
            if(isEmailNotValid(email)) throw new IllegalArgumentException("EMAIL IS NOT A EMAIL!");
            userJsonController.registerUser(email, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerAssociate(String email, String password) throws IOException {
        if(isEmailNotValid(email)) throw new IllegalArgumentException("EMAIL IS NOT A EMAIL!");
        userJsonController.registerAssociate(email, password);
    }

    public static boolean isEmailNotValid(String email) {
        return !Pattern.matches("^(.+)@(\\S+)$", email);
    }
}
