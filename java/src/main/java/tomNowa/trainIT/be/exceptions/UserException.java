package tomNowa.trainIT.be.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserException extends RuntimeException {

    @Getter
    private final String message;

    public UserException(final UserCodes exceptionCode) {
        log.error(exceptionCode.getMessage());
        this.message = exceptionCode.getMessage();
    }
}
