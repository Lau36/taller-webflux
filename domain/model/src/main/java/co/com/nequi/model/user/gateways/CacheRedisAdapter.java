package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface CacheRedisAdapter {
    Mono<User> getUserById(Long id);
    Mono<User> saveUser(User user);
}
