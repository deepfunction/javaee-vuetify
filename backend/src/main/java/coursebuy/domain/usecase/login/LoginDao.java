package coursebuy.domain.usecase.login;

import com.sun.istack.Nullable;
import coursebuy.domain.entity.AccessToken;
import coursebuy.domain.entity.User;

import javax.validation.constraints.NotNull;

public interface LoginDao {

    void addAccessToken(@NotNull AccessToken accessToken);

    void deleteExpiredTokens(@NotNull User user);

    @Nullable
    User findUserByEmail(@NotNull String email);

    @Nullable
    AccessToken findAccessTokenByDeviceId(@NotNull String uid);

    @Nullable
    AccessToken findAccessTokenByToken(@NotNull String token);
}
