package co.com.nequi.model.user.exceptions;

import co.com.nequi.model.user.enums.TechnicalMessage;

public class TallerWebfluxException extends RuntimeException {
    private TechnicalMessage technicalMessage;

    public TallerWebfluxException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage());
        this.technicalMessage = technicalMessage;
    }

    public TechnicalMessage getTechnicalMessage() {
        return technicalMessage;
    }
}
