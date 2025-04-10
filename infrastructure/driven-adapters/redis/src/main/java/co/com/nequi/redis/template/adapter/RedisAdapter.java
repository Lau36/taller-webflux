package co.com.nequi.redis.template.adapter;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.CacheRedisAdapter;
import co.com.nequi.redis.template.entity.UserCacheEntity;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class RedisAdapter extends ReactiveTemplateAdapterOperations<User, String, UserCacheEntity> implements CacheRedisAdapter {

    protected RedisAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, userCacheEntity -> mapper.map(userCacheEntity, User.class));
    }

    @Override
    public Mono<User> getUserById(Long id) {
        String keyId = generateKey(id);
        return findById(keyId);
    }

    @Override
    public Mono<User> saveUser(User user) {
        String keyId = generateKey(user.getApiId());
        return save(keyId, user);
    }

    public String generateKey(Long userId) {
        return String.format("user:id:%s", userId);

    }


}
