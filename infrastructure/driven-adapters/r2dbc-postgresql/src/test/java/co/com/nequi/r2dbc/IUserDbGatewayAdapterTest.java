package co.com.nequi.r2dbc;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IUserDbGateway;
import co.com.nequi.r2dbc.entity.UserEntity;
import co.com.nequi.r2dbc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IUserDbGatewayAdapterTest {

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserRepository repository;

    @Mock
    ObjectMapper mapper;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User(1L, 1L, "lore@gmail.com", "Lorena", "Jaimes");

        userEntity = new UserEntity(1L, 1L, "lore@gmail.com", "Lorena", "Jaimes");
    }

    @Test
    void mustFindValueByApiId() {

        Mockito.when(repository.findByApiId(1L)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findByApiId(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void findByNameTest() {
        String name = "Lorena";
        when(repository.findByFirstNameIgnoreCase(name)).thenReturn(Flux.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findByName(name);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }



}
