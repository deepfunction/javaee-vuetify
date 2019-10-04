package coursebuy.domain.usecase.authentication;

import javax.validation.constraints.NotNull;

public interface AuthenticationDao {

    boolean validateToken(@NotNull String token);
}
