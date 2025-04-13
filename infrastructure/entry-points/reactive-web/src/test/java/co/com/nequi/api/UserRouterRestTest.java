package co.com.nequi.api;

import co.com.nequi.api.handler.UserHandler;
import co.com.nequi.api.router.UserRouterRest;
import co.com.nequi.model.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapping;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class UserRouterRestTest {

    private WebTestClient webTestClient;

    private User user;
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userHandler = Mockito.mock(UserHandler.class);
        UserRouterRest userRouterRest = new UserRouterRest();
        RouterFunction<ServerResponse> routerFunction = userRouterRest.routerFunction(userHandler);
        webTestClient = WebTestClient.bindToController(routerFunction).build();

        user = new User(1L, 2L , "Tobias", "Funke", "tobias.funke@example.com");
    }

    @Test
    void testListAllUsers() {
       when(userHandler.listAllUsers(any())).thenReturn(ServerResponse.ok().bodyValue(List.of(user)));

        webTestClient.get()
                .uri("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> {
                    assertThat(users).hasSize(1);
                    assertThat(users.getFirst().getFirstName()).isEqualTo("Tobias");
                });
    }

    @Test
    void testFindUserById() {
        when(userHandler.findUserById(any())).thenReturn(ServerResponse.ok().bodyValue(user));

        webTestClient.get()
                .uri("/api/users/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(userResponse -> {
                    assertThat(userResponse.getFirstName()).isEqualTo("Tobias");
                    assertThat(userResponse.getId()).isEqualTo(1L);
                });
    }

    @Test
    void testFindUsersByName() {
        when(userHandler.findUsersByName(any())).thenReturn(ServerResponse.ok().bodyValue(user));
        webTestClient.get()
                .uri("/api/users/byName/Tobias")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> {
                    assertThat(users).hasSize(1);
                    assertThat(users.get(0).getFirstName()).isEqualTo("Tobias");
                });
    }

    @Test
    void testCreateUser() {
        when(userHandler.createUser(any())).thenReturn(ServerResponse.ok().bodyValue(user));

        webTestClient.post()
                .uri("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(userResponse -> {
                    assertThat(userResponse.getFirstName()).isEqualTo("Tobias");
                    assertThat(userResponse.getId()).isEqualTo(1L);
                });
    }

}
