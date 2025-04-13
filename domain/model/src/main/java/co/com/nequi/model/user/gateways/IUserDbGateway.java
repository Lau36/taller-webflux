package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserDbGateway {
    Mono<User> save(User user);
    Flux<User> findByName(String name);
    Mono<User> findByApiId(Long id);
    Flux<User> findAll();
}
