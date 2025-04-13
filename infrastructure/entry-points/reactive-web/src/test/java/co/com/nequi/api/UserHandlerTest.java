package co.com.nequi.api;

import co.com.nequi.api.handler.UserHandler;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserException;
import co.com.nequi.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserHandler userHandler;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, 1L , "Alice", "Funke", "Alice.funke@example.com");
    }

    @Test
    void shouldReturnAllUsers() {

        when(userUseCase.findAllUsers()).thenReturn(Flux.just(user));

        ServerRequest request = MockServerRequest.builder().build();

        Mono<ServerResponse> response = userHandler.listAllUsers(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(userUseCase).findAllUsers();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userUseCase.saveUserByApi(1L)).thenReturn(Mono.just(user));

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        Mono<ServerResponse> response = userHandler.createUser(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(userUseCase).saveUserByApi(1L);
    }

    @Test
    void shouldHandleUserExceptionWhenCreatingUser() {
        when(userUseCase.saveUserByApi(1L))
                .thenReturn(Mono.error(new UserException("User already exists")));

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        Mono<ServerResponse> response = userHandler.createUser(request);

        StepVerifier.create(response)
                .expectNextMatches(res -> res.statusCode().equals(HttpStatus.BAD_REQUEST))
                .verifyComplete();
    }

    @Test
    void shouldReturnUsersByName() {
        when(userUseCase.findUsersByName("Alice")).thenReturn(Flux.just(user));

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("name", "Alice")
                .build();

        Mono<ServerResponse> response = userHandler.findUsersByName(request);

        StepVerifier.create(response)
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(userUseCase).findUsersByName("Alice");
    }

    @Test
    void shouldReturnUserById() {
        when(userUseCase.findUserById(1L)).thenReturn(Mono.just(user));

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        Mono<ServerResponse> response = userHandler.findUserById(request);

        StepVerifier.create(response)
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void shouldHandleUserNotFound() {
        when(userUseCase.findUserById(1L))
                .thenReturn(Mono.error(new UserException("User not found")));

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();

        Mono<ServerResponse> response = userHandler.findUserById(request);

        StepVerifier.create(response)
                .expectNextMatches(res -> res.statusCode().equals(HttpStatus.NOT_FOUND))
                .verifyComplete();
    }
}
