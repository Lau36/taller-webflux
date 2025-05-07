package co.com.nequi.api.handler;

import co.com.nequi.api.dto.ErrorResponse;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.TechnicalMessage;
import co.com.nequi.model.user.exceptions.ApiExternalException;
import co.com.nequi.model.user.exceptions.BusinessException;
import co.com.nequi.model.user.exceptions.TechnicalException;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserUseCase userUseCase;

    private static final Logger log = LoggerFactory.getLogger(UserHandler.class.getName());

    public Mono<ServerResponse> listAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok().body(userUseCase.findAllUsers(), User.class)
                .doOnError(ex -> log.error("Error getting all users: {}", ex.getMessage()))
                .onErrorResume(
                        TechnicalException.class, ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())
                )
                .onErrorResume(
                        Throwable.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue(new ErrorResponse(ex.getMessage(), 500))
                );
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest){

        return Mono.defer(() ->{
            String id = serverRequest.pathVariable("id");
            return userUseCase.saveUserByApi(Long.parseLong(id));})
                .doOnSuccess(user -> log.info("User created with email: {}", user.getEmail()))
                .flatMap( user -> ServerResponse.ok().bodyValue(user))
                .onErrorMap(NumberFormatException.class, ex ->
                        new BusinessException(TechnicalMessage.ID_NOT_VALID))
                .onErrorResume(BusinessException.class,
                        ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())
                )
                .doOnError(ex -> log.error("Error creating user: {}", ex.getMessage()))
                .onErrorResume(
                        ApiExternalException.class, ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())
                )
                .onErrorResume(
                        TechnicalException.class, ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())

                )
                .onErrorResume(
                        Throwable.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue(new ErrorResponse(ex.getMessage(), 500))
                );
    }

    public Mono<ServerResponse> findUsersByName(ServerRequest serverRequest) {
        return Mono.defer(() -> {
            String name = serverRequest.pathVariable("name");
            return userUseCase.findUsersByName(name).collectList();

        })
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .onErrorResume(BusinessException.class,
                        ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())
                )
                .doOnError(ex -> log.error("Error filtering by name: {}", ex.getMessage()))
                .onErrorResume(
                        TechnicalException.class, ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())

                )
                .onErrorResume(
                        Throwable.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue(new ErrorResponse(ex.getMessage(), 500))
                );
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        return Mono.defer(() ->{
            String userId = serverRequest.pathVariable("id");
            return userUseCase.findUserById(Long.parseLong(userId));
        }).flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(BusinessException.class,
                        ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode()))
                .doOnError(ex -> log.error("Error filtering user by id: {}", ex.getMessage()))
                .onErrorResume(
                        TechnicalException.class, ex -> handleErrorResponse(ex.getTechnicalMessage().getMessage(),
                                ex.getTechnicalMessage().getCode())
                )
                .onErrorResume(
                        Throwable.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue(new ErrorResponse(TechnicalMessage.SERVER_ERROR.getMessage(), 500))
                );
    }

    private Mono<ServerResponse> handleErrorResponse(String message, int code) {
        return ServerResponse.status(code)
                .bodyValue(new ErrorResponse(message, code));
    }


}
