package co.com.nequi.consumer.rest;

import co.com.nequi.consumer.dto.Data;
import co.com.nequi.consumer.mapper.UserMapperWebClient;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.ErrorUser;
import co.com.nequi.model.user.exceptions.WebClientException;
import co.com.nequi.model.user.gateways.UserWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.just(
                                new WebClientException(String.format(ErrorUser.USER_BY_ID_NOT_FOUND.getMessage(), userId))
                                )
                        )
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.just(new WebClientException(ErrorUser.ERROR_EXTERNAL_SERVICE.getMessage())))
                .bodyToMono(Data.class)
                .map(userMapperWebClient::toUser);
    }

}
