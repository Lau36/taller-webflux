package co.com.nequi.usecase.user;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.ErrorUser;
import co.com.nequi.model.user.exceptions.UserException;
import co.com.nequi.model.user.gateways.ICacheGateway;
import co.com.nequi.model.user.gateways.IUserSqsGateway;
import co.com.nequi.model.user.gateways.IUserDbGateway;
import co.com.nequi.model.user.gateways.UserWebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class UserUseCase {

    private final IUserDbGateway userDbGateway;
    private final UserWebClient userWebClient;
    private final ICacheGateway cacheGateway;
    private final IUserSqsGateway userSqsGateway;
    //private final static Logger logger = LoggerFactory.getLogger(MyClass.class);

    public UserUseCase(IUserDbGateway userDbGateway, UserWebClient userWebClient, ICacheGateway cacheGateway, IUserSqsGateway userSqsGateway) {
        this.userDbGateway = userDbGateway;
        this.userWebClient = userWebClient;
        this.cacheGateway = cacheGateway;
        this.userSqsGateway = userSqsGateway;
    }

    public Mono<User> saveUserByApi(Long id) {
        return userDbGateway.findByApiId(id)
                .switchIfEmpty(
                userWebClient.getById(id)
                        .flatMap(user -> userDbGateway.save(user)
                                .flatMap(userSaved -> userSqsGateway.send(userSaved).thenReturn(userSaved)
                                        .flatMap(cacheGateway::saveUser)
                                )
                )

        );
    }

    public Mono<User> findUserById(Long id) {
        return cacheGateway.getUserById(id)
                .switchIfEmpty(userDbGateway.findByApiId(id)
                        .flatMap(cacheGateway::saveUser)
                        .switchIfEmpty(Mono.error(
                                new UserException(String.format(ErrorUser.USER_BY_ID_NOT_FOUND.getMessage(), id))
                                                )
                                )

                );
    }

    public Flux<User> findAllUsers() {
        return userDbGateway.findAll();
    }

    public Flux<User> findUsersByName(String name) {
        return userDbGateway.findByName(name)
                .switchIfEmpty(Flux.error(
                        new UserException(String.format(ErrorUser.USER_BY_NAME_NOT_FOUND.getMessage(), name))
                )
        );
    }



}
