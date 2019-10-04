package coursebuy.domain.usecase.utils;

public class BaseResult {

    private String message;

    private boolean isSuccess;

    public BaseResult(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
