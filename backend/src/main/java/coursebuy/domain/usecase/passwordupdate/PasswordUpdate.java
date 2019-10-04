package coursebuy.domain.usecase.passwordupdate;

import coursebuy.domain.usecase.utils.EmailSender;
import coursebuy.domain.usecase.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

@RequestScoped
public class PasswordUpdate {

    private static final Logger LOGGER = LogManager.getLogger(PasswordUpdate.class);
    @Inject
    private PasswordUpdateDao passwordUpdateDao;
    @Inject
    private EmailSender emailSender;

    public PasswordUpdateResult sendPasswordUpdateLink(String email, String language) {
        Locale locale = new Locale.Builder().setLanguage(language).build();
        if (!Validator.validateEmail(email)) {
            return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("validationError"), false);
        } else {
            if (passwordUpdateDao.findUserByEmail(email)) {
                if (emailSender.sendPasswordUpdateLink(email)) {
                    return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("passwordUpdateLink"), true);
                } else {
                    return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
                }
            } else {
                return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("emailNotFound"), false);
            }
        }
    }

    public PasswordUpdateResult updatePassword(String code, String password, String language) {
        Locale locale = new Locale.Builder().setLanguage(language).build();
        if (!Validator.validatePassword(password)) {
            return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("validationError"), false);
        } else {
            try {
                String[] codes = code.split(":");
                String encodedEmail = codes[0];
                String encodedLifeTime = codes[1];
                Base64.Decoder decoder = Base64.getDecoder();
                String email = new String(decoder.decode(encodedEmail));
                long dateInMillis = ByteBuffer.wrap(decoder.decode(encodedLifeTime)).getLong();
                LocalDateTime lifeTime = Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (lifeTime.isBefore(LocalDateTime.now())) {
                    return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("linkExpired"), false);
                } else {
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encodedPassword = passwordEncoder.encode(password);
                    if (passwordUpdateDao.updatePassword(email, encodedPassword) == 1) {
                        return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("passwordUpdated"), true);
                    } else {
                        return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
                    }
                }
            } catch (RuntimeException e) {
                LOGGER.fatal("Failed to update the password.", e);
                return new PasswordUpdateResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
            }
        }
    }
}
