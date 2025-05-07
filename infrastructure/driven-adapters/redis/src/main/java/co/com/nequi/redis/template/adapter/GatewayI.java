package co.com.nequi.redis.template.adapter;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.TechnicalMessage;
import co.com.nequi.model.user.exceptions.TechnicalException;
import co.com.nequi.model.user.gateways.ICacheGateway;
import co.com.nequi.redis.template.entity.UserCacheEntity;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class GatewayI extends ReactiveTemplateAdapterOperations<User, String, UserCacheEntity> implements ICacheGateway {

    protected GatewayI(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, userCacheEntity -> mapper.map(userCacheEntity, User.class));
    }

    @Override
    public Mono<User> getUserById(Long id) {
        String keyId = generateKey(id);
        return findById(keyId)
                .onErrorResume(ex ->
                        Mono.error(new TechnicalException(TechnicalMessage.CACHE_ERROR)));
    }

    @Override
    public Mono<User> saveUser(User user) {
        String keyId = generateKey(user.getApiId());
        return save(keyId, user)
                .onErrorResume(ex ->
                        Mono.error(new TechnicalException(TechnicalMessage.CACHE_ERROR)));
    }

    public String generateKey(Long userId) {
        return String.format("user:id:%s", userId);

    }


}
