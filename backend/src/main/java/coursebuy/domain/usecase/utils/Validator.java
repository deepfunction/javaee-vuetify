package coursebuy.domain.usecase.utils;

import java.util.ResourceBundle;

public class Validator {

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        } else {
            String passwordPattern = ResourceBundle.getBundle("regex").getString("password");
            return password.matches(passwordPattern);
        }
    }

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        } else {
            String emailPattern = ResourceBundle.getBundle("regex").getString("email");
            return email.matches(emailPattern);
        }
    }
}
