package co.com.nequi.sqs.listener;

import co.com.nequi.model.user.User;
import co.com.nequi.usecase.queue.QueueUseCase;
import co.com.nequi.usecase.user.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final QueueUseCase queueUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        System.out.println( "ESTOY EN EL PROCESSOR " + message.body());
        return Mono.just(message.body())
                .flatMap(this::mapToUser)
                .flatMap(queueUseCase::saveUser)
                .then();
    }

    public Mono<User> mapToUser(String messge) {
        User user = objectMapper.convertValue(messge, User.class);
        return Mono.just(user.toBuilder()
                        .id(user.getId())
                        .apiId(user.getApiId())
                        .firstName(user.getFirstName().toUpperCase())
                        .lastName(user.getLastName().toUpperCase())
                        .email(user.getEmail().toUpperCase())
                        .build()
        );

    }

}
