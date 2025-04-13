package co.com.nequi.usecase.queue;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IDynamoGateway;
import reactor.core.publisher.Mono;

public class QueueUseCase {
    private final IDynamoGateway gateway;

    public QueueUseCase(IDynamoGateway gateway) {
        this.gateway = gateway;
    }

    public Mono<User> saveUser(User infoUser) {
        return gateway.saveUser(infoUser);
    }
}
