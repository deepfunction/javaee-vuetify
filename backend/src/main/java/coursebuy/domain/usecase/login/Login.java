package coursebuy.domain.usecase.login;

import coursebuy.domain.entity.AccessToken;
import coursebuy.domain.entity.User;
import coursebuy.domain.usecase.utils.JsonWebToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@RequestScoped
public class Login {

    @Inject
    private LoginDao loginDao;

    private Locale locale;

    public LoginResult login(String email, String password, String deviceId, String language) {
        locale = new Locale.Builder().setLanguage(language).build();
        User user = loginDao.findUserByEmail(email);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AccessToken accessToken;
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("userNotFound"), false);
        } else {
            if (deviceId == null || deviceId.equals("") || deviceId.equals("null")) {
                accessToken = createNewAccessToken(user, UUID.randomUUID().toString());
                loginDao.addAccessToken(accessToken);
                return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("loginSuccessful"), true, accessToken);
            } else {
                loginDao.deleteExpiredTokens(user);
                accessToken = loginDao.findAccessTokenByDeviceId(deviceId);
            }
            if (accessToken == null) {
                accessToken = createNewAccessToken(user, deviceId);
                loginDao.addAccessToken(accessToken);
                return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("loginSuccessful"), true, accessToken);
            } else {
                return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("loginSuccessful"), true, accessToken);
            }
        }
    }

    private AccessToken createNewAccessToken(User user, String deviceId) {
        String token = JsonWebToken.getInstance().generatePrivateKey(user.getEmail(), user.getPassword(), LocalDateTime.now().toString());
        if (deviceId.length() > 36) {
            return new AccessToken(token, deviceId, LocalDateTime.now().plusYears(99), user);
        } else {
            return new AccessToken(token, deviceId, LocalDateTime.now().plusDays(14), user);
        }
    }

    public LoginResult loginWithToken(String token, String language) {
        locale = new Locale.Builder().setLanguage(language).build();
        AccessToken accessToken = loginDao.findAccessTokenByToken(token);
        if (accessToken != null) {
            return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("loginSuccessful"), true, accessToken);
        } else {
            return new LoginResult(ResourceBundle.getBundle("i18n.messages", locale).getString("userNotFound"), false);
        }
    }
}
