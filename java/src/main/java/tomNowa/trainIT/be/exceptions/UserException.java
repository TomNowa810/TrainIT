package tomNowa.trainIT.be.exceptions;

import lombok.Getter;

public class UserException extends RuntimeException {

    @Getter
    private final String message;

    public UserException(final UserCodes exceptionCode) {
        this.message = exceptionCode.getMessage();
    }
}
