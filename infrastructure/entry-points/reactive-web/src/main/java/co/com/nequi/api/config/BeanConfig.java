package co.com.nequi.api.config;

import co.com.nequi.model.user.gateways.CacheRedisAdapter;
import co.com.nequi.model.user.gateways.SqsUserGateway;
import co.com.nequi.model.user.gateways.UserPersistencePort;
import co.com.nequi.model.user.gateways.UserWebClient;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BeanConfig {
    private UserPersistencePort userPersistencePort;
    private UserWebClient userWebClient;
    private CacheRedisAdapter cacheRedisAdapter;
    private SqsUserGateway sqsUserGateway;

    @Bean
    public UserUseCase userUseCase() {
        return new UserUseCase(userPersistencePort, userWebClient, cacheRedisAdapter, sqsUserGateway);
    }
}
