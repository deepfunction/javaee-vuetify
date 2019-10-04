package coursebuy.domain.usecase.registration;

import coursebuy.domain.usecase.utils.BaseResult;

public class RegistrationResult extends BaseResult {

    public RegistrationResult(String message, boolean isSuccess) {
        super(message, isSuccess);
    }
}
