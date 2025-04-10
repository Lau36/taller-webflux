package co.com.nequi.consumer.rest;

import co.com.nequi.consumer.dto.Data;
import co.com.nequi.consumer.mapper.UserMapperWebClient;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.UserWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserWebClient {
    private final WebClient client;
    private final UserMapperWebClient userMapperWebClient;

    public Mono<User> getById(Long userId) {
        return client
                .get()
                .uri("/{userId}", userId)
                .retrieve()
                .bodyToMono(Data.class)
                .map(userMapperWebClient::toUser);
    }

}
