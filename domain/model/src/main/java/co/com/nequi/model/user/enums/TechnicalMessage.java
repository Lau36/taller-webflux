package co.com.nequi.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TechnicalMessage {
    USER_BY_ID_NOT_FOUND("El usuario con el id ingresado no existe", 404),
    USER_BY_NAME_NOT_FOUND("El usuario con el nombre ingresado no existe en la base de datos", 404),
    ERROR_EXTERNAL_SERVICE("Ocurrió un error con el servicio externo para obtener el usuario", 500),
    TECHNICAL_ERROR("Ocurrio un error técnico", 500),
    SERVER_ERROR("Ocurrió un error con el servidor", 500),
    SQS_ERROR("Ocurrió un error con el servicio de sqs", 500),
    CACHE_ERROR("Ocurrió un error con el servicio de caché", 500),
    DB_ERROR("Ocurrió un error con la base de datos", 500),
    ID_NOT_VALID("El id no es valido", 500),
    ERROR_MAPPING("Error mapeando la información", 500);

    private final String message;
    private int code;

}
