package co.com.nequi.r2dbc.repository;

import co.com.nequi.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    Flux<UserEntity> findByFirstNameIgnoreCase(String name);
    Mono<UserEntity> findByApiId(Long apiId);

}
