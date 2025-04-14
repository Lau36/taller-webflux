package co.com.nequi.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UsersErrors {
    USER_BY_ID_NOT_FOUND("El usuario con el id %s no existe"),
    USER_BY_NAME_NOT_FOUND("El usuario con el nombre %s no existe en la base de datos"),
    ERROR_EXTERNAL_SERVICE("Ocurrió un error con el servicio externo para obtener el usuario");

    private final String message;

}
