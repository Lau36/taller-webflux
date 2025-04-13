package co.com.nequi.api.handler;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserException;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return ServerResponse.ok().body(userUseCase.findAllUsers(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {

        return Mono.defer(() ->{
            String id = serverRequest.pathVariable("id");
            return userUseCase.saveUserByApi(Long.parseLong(id));
        }).doOnSuccess(user -> log.info("User created with email: {}", user.getEmail()))
                .flatMap( user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(UserException.class,
                        ex -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume(
                        e -> {
                            log.error(e.getMessage(), e);
                            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage());
                        }
                );
    }

    public Mono<ServerResponse> findUsersByName(ServerRequest serverRequest) {
        return Mono.defer(() -> {
            String name = serverRequest.pathVariable("name");
            return userUseCase.findUsersByName(name)
                    .collectList()
                    .flatMap(users -> ServerResponse.ok().bodyValue(users))
                    .onErrorResume(UserException.class,
                            ex -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(ex.getMessage()))
                    .onErrorResume(e -> {
                        log.error(e.getMessage(), e);
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage());
                    });
        });
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        return Mono.defer(() ->{
            String userId = serverRequest.pathVariable("id");
            return userUseCase.findUserById(Long.parseLong(userId))
                    .flatMap(user -> ServerResponse.ok().bodyValue(user))
                    .onErrorResume(UserException.class,
                            ex -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(ex.getMessage()))
                    .onErrorResume(
                            e -> {
                                log.error(e.getMessage(), e);
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage());
                            }
                    );
        });
    }
}
