package co.com.nequi.config;

import co.com.nequi.model.user.gateways.CacheRedisAdapter;
import co.com.nequi.model.user.gateways.DynamoGateway;
import co.com.nequi.model.user.gateways.SqsUserGateway;
import co.com.nequi.model.user.gateways.UserPersistencePort;
import co.com.nequi.model.user.gateways.UserWebClient;
import co.com.nequi.usecase.queue.QueueUseCase;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BeanConfig {
    private UserPersistencePort userPersistencePort;
    private UserWebClient userWebClient;
    private CacheRedisAdapter cacheRedisAdapter;
    private SqsUserGateway sqsUserGateway;
    private DynamoGateway dynamoGateway;

    @Bean
    public UserUseCase userUseCase() {
        return new UserUseCase(userPersistencePort, userWebClient, cacheRedisAdapter, sqsUserGateway);
    }

    @Bean
    public QueueUseCase queueUseCase() {
        return new QueueUseCase(dynamoGateway);
    }

}
