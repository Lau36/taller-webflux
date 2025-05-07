package co.com.nequi.model.user.exceptions;

import co.com.nequi.model.user.enums.TechnicalMessage;

public class ApiExternalException extends TallerWebfluxException {
    public ApiExternalException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }
}
