package coursebuy.domain.usecase.passwordupdate;

import coursebuy.domain.usecase.utils.BaseResult;

public class PasswordUpdateResult extends BaseResult {

    PasswordUpdateResult(String message, boolean isSuccess) {
        super(message, isSuccess);
    }
}
