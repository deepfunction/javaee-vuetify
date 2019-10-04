package coursebuy.domain.usecase.logout;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Logout {

    @Inject
    private LogoutDao logoutDao;

    public void deleteAccessTokenByDeviceId(String deviceId) {
        logoutDao.deleteAccessTokenByDeviceId(deviceId);
    }
}
