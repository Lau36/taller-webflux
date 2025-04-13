package co.com.nequi.config;

import co.com.nequi.model.user.gateways.ICacheGateway;
import co.com.nequi.model.user.gateways.IDynamoGateway;
import co.com.nequi.model.user.gateways.IUserSqsGateway;
import co.com.nequi.model.user.gateways.IUserDbGateway;
import co.com.nequi.model.user.gateways.UserWebClient;
import co.com.nequi.usecase.queue.QueueUseCase;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BeanConfig {
    private IUserDbGateway IUserDbGateway;
    private UserWebClient userWebClient;
    private ICacheGateway ICacheGateway;
    private IUserSqsGateway iUserSqsGateway;
    private IDynamoGateway IDynamoGateway;

    @Bean
    public UserUseCase userUseCase() {
        return new UserUseCase(IUserDbGateway, userWebClient, ICacheGateway, iUserSqsGateway);
    }

    @Bean
    public QueueUseCase queueUseCase() {
        return new QueueUseCase(IDynamoGateway);
    }

}
