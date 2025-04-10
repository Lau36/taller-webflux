package co.com.nequi.api.handler;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserException;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    private final UserUseCase userUseCase;

    public Mono<ServerResponse> listAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok().body(userUseCase.findAllUsers(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {

        return Mono.defer(() ->{
            String id = serverRequest.pathVariable("id");
            return userUseCase.saveUserByApi(Long.parseLong(id));
        }).flatMap( user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(UserException.class,
                        ex -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()));
    }

    public Mono<ServerResponse> findUsersByName(ServerRequest serverRequest) {

        return Mono.defer(() ->{
            String name = serverRequest.pathVariable("name");
            return ServerResponse.ok().body(userUseCase.findUsersByName(name), User.class);
        });
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        return Mono.defer(() ->{
            String userId = serverRequest.pathVariable("id");
            return userUseCase.findUserById(Long.parseLong(userId))
                    .flatMap(user -> ServerResponse.ok().bodyValue(user))
                    .onErrorResume(UserException.class,
                            ex -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()));
        });
    }
}
