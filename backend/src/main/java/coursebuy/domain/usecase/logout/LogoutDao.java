package coursebuy.domain.usecase.logout;

import coursebuy.domain.entity.User;

import javax.validation.constraints.NotNull;

public interface LogoutDao {

    void deleteAccessTokenByDeviceId(@NotNull String deviceId);

}
