package co.com.nequi.r2dbc;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IUserDbGateway;
import co.com.nequi.r2dbc.entity.UserEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import co.com.nequi.r2dbc.repository.UserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
        Long,
        UserRepository
> implements IUserDbGateway {
    public MyReactiveRepositoryAdapter(UserRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
    }


    @Override
    public Flux<User> findByName(String name) {
        return repository.findByFirstNameIgnoreCase(name)
                .map(this::toEntity);
    }

    @Override
    public Mono<User> findByApiId(Long id) {
        return repository.findByApiId(id).map(this::toEntity);
    }
}
