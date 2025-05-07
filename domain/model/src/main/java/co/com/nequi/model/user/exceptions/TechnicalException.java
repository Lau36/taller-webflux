package co.com.nequi.model.user.exceptions;

import co.com.nequi.model.user.enums.TechnicalMessage;

public class TechnicalException extends TallerWebfluxException {
    public TechnicalException(TechnicalMessage message) {
        super(message);
    }
}
