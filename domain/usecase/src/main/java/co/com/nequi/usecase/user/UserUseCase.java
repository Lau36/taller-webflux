package co.com.nequi.usecase.user;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.TechnicalMessage;
import co.com.nequi.model.user.exceptions.BusinessException;
import co.com.nequi.model.user.exceptions.TechnicalException;
import co.com.nequi.model.user.gateways.ICacheGateway;
import co.com.nequi.model.user.gateways.IUserSqsGateway;
import co.com.nequi.model.user.gateways.IUserDbGateway;
import co.com.nequi.model.user.gateways.UserWebClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserUseCase {

    private final IUserDbGateway userDbGateway;
    private final UserWebClient userWebClient;
    private final ICacheGateway cacheGateway;
    private final IUserSqsGateway userSqsGateway;

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
                        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.USER_BY_ID_NOT_FOUND)))

                );
    }

    public Flux<User> findAllUsers() {
        return userDbGateway.findAll();
    }

    public Flux<User> findUsersByName(String name) {
        return userDbGateway.findByName(name)
                .switchIfEmpty(Flux.error(
                        new BusinessException(TechnicalMessage.USER_BY_NAME_NOT_FOUND
                ))
        );
    }



}
