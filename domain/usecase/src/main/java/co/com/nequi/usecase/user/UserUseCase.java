package co.com.nequi.usecase.user;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserException;
import co.com.nequi.model.user.gateways.CacheRedisAdapter;
import co.com.nequi.model.user.gateways.DynamoGateway;
import co.com.nequi.model.user.gateways.SqsUserGateway;
import co.com.nequi.model.user.gateways.UserPersistencePort;
import co.com.nequi.model.user.gateways.UserWebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserWebClient userWebClient;
    private final CacheRedisAdapter cacheRedisAdapter;
    private final SqsUserGateway sqsUserGateway;

    public UserUseCase(UserPersistencePort userPersistencePort, UserWebClient userWebClient, CacheRedisAdapter cacheRedisAdapter, SqsUserGateway sqsUserGateway) {
        this.userPersistencePort = userPersistencePort;
        this.userWebClient = userWebClient;
        this.cacheRedisAdapter = cacheRedisAdapter;
        this.sqsUserGateway = sqsUserGateway;
    }

    public Mono<User> saveUserByApi(Long id) {
        return userWebClient.getById(id)
                .flatMap( user -> validateExistsUser(id)
                        .switchIfEmpty(userPersistencePort.save(user))
                        .then(cacheRedisAdapter.saveUser(user))
                        .then(sqsUserGateway.send(user))
                        .thenReturn(user)
                );
    }

    public Mono<User> validateExistsUser(Long id) {
        return userPersistencePort.findById(id);
    }

    public Mono<User> findUserById(Long id) {
        return cacheRedisAdapter.getUserById(id)
                .switchIfEmpty(userPersistencePort.findById(id)
                        .flatMap(cacheRedisAdapter::saveUser)
                        .switchIfEmpty(Mono.error(new UserException("User does not exist in database", 400)))

                );
    }

    public Flux<User> findAllUsers() {
        return userPersistencePort.findAll();
    }

    public Flux<User> findUsersByName(String name) {
        return userPersistencePort.findByName(name);
    }



}
