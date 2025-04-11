package co.com.nequi.usecase.queue;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.DynamoGateway;
import reactor.core.publisher.Mono;

public class QueueUseCase {
    private final DynamoGateway gateway;

    public QueueUseCase(DynamoGateway gateway) {
        this.gateway = gateway;
    }

    public Mono<User> saveUser(User infoUser) {
        return gateway.saveUser(infoUser);
    }
}
