package co.com.nequi.r2dbc.adapter;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.UserPersistencePort;
import co.com.nequi.r2dbc.mapper.UserMapper;
import co.com.nequi.r2dbc.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
@Slf4j
public class UserAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(userMapper.toUserEntity(user))
                .map(userMapper::toUser);
    }

    @Override
    public Flux<User> findByName(String name) {
        return userRepository.findByFirstName(name).map(userMapper::toUser);
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findByApiId(id).map(userMapper::toUser);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll().map(userMapper::toUser);
    }
}
