package coursebuy.domain.usecase.passwordupdate;

import javax.validation.constraints.NotNull;

public interface PasswordUpdateDao {

    int updatePassword(@NotNull String email, @NotNull String password);

    boolean findUserByEmail(@NotNull String email);
}
