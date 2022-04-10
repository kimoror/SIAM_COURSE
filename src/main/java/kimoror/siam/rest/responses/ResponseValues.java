package kimoror.siam.rest.responses;

public enum ResponseValues {
    ROLE_NOT_FOUND_IN_DB(-2, "Error: Role is not found."),
    DEFAULT_ROLE_NOT_FOUND_IN_DB(-3, "Error: default role \"user\" is not found."),
    SUCCESSES(0, "OK"),
    EMAIL_ALREADY_USED(-1, "Error: Email is already in use!"),
    BAD_PARAMETERS(-4, "Required parameters not found"),
    CONTENT_NOT_FOUND(-5, "Content not found found"),
    CONTENT_ALREADY_EXISTS(-6, "CONTENT_ALREADY_EXISTS"),
    CURRENT_USER_NOT_FOUND(-7, "Current user not found");

    private final int errorCode;
    private final String errorMessage;


    ResponseValues(int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
