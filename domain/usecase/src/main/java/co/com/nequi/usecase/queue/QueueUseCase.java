package co.com.nequi.usecase.queue;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IDynamoGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class QueueUseCase {
    private final IDynamoGateway gateway;

    public Mono<User> saveUser(User infoUser) {
        return gateway.saveUser(infoUser);
    }
}
