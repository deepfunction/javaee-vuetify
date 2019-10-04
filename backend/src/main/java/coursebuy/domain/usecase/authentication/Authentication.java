package coursebuy.domain.usecase.authentication;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Authentication {

    @Inject
    private AuthenticationDao authenticationDao;

    public boolean validateToken(String token) {
        return authenticationDao.validateToken(token);
    }
}
