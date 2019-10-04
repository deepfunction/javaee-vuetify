package coursebuy.domain.usecase.registration;

import coursebuy.domain.usecase.utils.EmailSender;
import coursebuy.domain.usecase.utils.Validator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Locale;
import java.util.ResourceBundle;

@RequestScoped
public class Registration {

    @Inject
    private RegistrationDao registrationDao;
    @Inject
    private EmailSender emailSender;

    public RegistrationResult register(String email, String password, String language) {
        Locale locale = new Locale.Builder().setLanguage(language).build();
        if (!Validator.validateEmail(email) || !Validator.validatePassword(password)) {
            return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("validationError"), false);
        } else {
            boolean emailExist = registrationDao.checkExistenceEmail(email);
            if (emailExist) {
                return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("emailExist"), false);
            } else {
                if (emailSender.sendRegistrationConfirmLink(email, password)) {
                    return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("emailSent"), true);
                } else {
                    return new RegistrationResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
                }
            }
        }
    }
}
