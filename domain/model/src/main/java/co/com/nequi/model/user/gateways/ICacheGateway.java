package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface ICacheGateway {
    Mono<User> getUserById(Long id);
    Mono<User> saveUser(User user);
}
