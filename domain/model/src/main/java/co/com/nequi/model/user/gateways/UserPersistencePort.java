package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> save(User user);
    Flux<User> findByName(String name);
    Mono<User> findById(Long id);
    Flux<User> findAll();
}
