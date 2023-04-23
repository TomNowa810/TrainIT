package tomNowa.trainIT.be.exceptions;

import lombok.Getter;

public enum UserCodes {

    SUCCESSFUL_USER_CREATION("User wurde erfolgreich registriert!"),
    LOGIN_ERROR_USER_NOT_REGISTERED("Dieser Username ist nicht regestriert!"),
    LOGIN_ERROR_PASSWORD_INCORRECT("Das angegebene Password nicht nicht korrekt!"),
    CREATION_ERROR_USER_ALREADY_EXIST("Dieser Username existiert bereits!");


    @Getter
    private final String message;

    UserCodes(final String message) {
        this.message = message;
    }

}
