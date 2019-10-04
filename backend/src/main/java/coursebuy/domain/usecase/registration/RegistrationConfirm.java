package coursebuy.domain.usecase.registration;

import coursebuy.domain.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

@RequestScoped
public class RegistrationConfirm {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationConfirm.class);

    public RegistrationResult registrationConfirm(String code, String language) {
        Locale locale = new Locale.Builder().setLanguage(language).build();
        try {
            String[] codes = code.split(":");
            String encodedEmail = codes[0];
            String encodedPassword = codes[1];
            String encodedLifeTime = codes[2];
            Base64.Decoder decoder = Base64.getDecoder();
            String email = new String(decoder.decode(encodedEmail));
            String password = new String(decoder.decode(encodedPassword));
            long dateInMillis = ByteBuffer.wrap(decoder.decode(encodedLifeTime)).getLong();
            LocalDateTime lifeTime = Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (lifeTime.isBefore(LocalDateTime.now())) {
                return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("linkExpired"), false);
            } else {
                return addUser(email, password, locale);
            }
        } catch (RuntimeException e) {
            LOGGER.fatal("Failed to confirm registration.", e);
            return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
        }
    }

    private RegistrationResult addUser(String email, String password, Locale locale) {
        long userId;
        RegistrationDao registrationDao = CDI.current().select(RegistrationDao.class).get();
        int index = email.indexOf('@');
        String username = email.substring(0, index);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);
        if (registrationDao.checkExistenceEmail(user.getEmail())) {
            return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("emailExist"), false);
        } else {
            if (registrationDao.addUser(user) != 0) {
                return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("registrationSuccess"), true);
            } else {
                return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
            }
        }
    }
}
