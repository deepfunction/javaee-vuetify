package coursebuy.domain.usecase.registration;

import coursebuy.domain.entity.User;

import javax.validation.constraints.NotNull;

public interface RegistrationDao {

    long addUser(@NotNull User user);

    boolean checkExistenceEmail(@NotNull String email);
}
