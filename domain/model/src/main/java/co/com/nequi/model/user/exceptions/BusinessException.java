package co.com.nequi.model.user.exceptions;

import co.com.nequi.model.user.enums.TechnicalMessage;

public class BusinessException extends TallerWebfluxException {

    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }
}
