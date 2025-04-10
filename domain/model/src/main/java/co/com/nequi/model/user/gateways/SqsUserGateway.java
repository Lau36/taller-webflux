package co.com.nequi.model.user.gateways;

import reactor.core.publisher.Mono;

public interface SqsUserGateway {
    Mono<Void> sendMessage(String topic, String message);
}
