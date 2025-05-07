package co.com.nequi.sqs.listener;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.enums.TechnicalMessage;
import co.com.nequi.model.user.exceptions.TechnicalException;
import co.com.nequi.usecase.queue.QueueUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final QueueUseCase queueUseCase;
    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(User.class);

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.just(message.body())
                .flatMap(this::mapToUser)
                .flatMap(queueUseCase::saveUser)
                .then();
    }

    public Mono<User> mapToUser(String messgeBody) {
        try{
            User user = objectMapper.readValue(messgeBody, User.class);
            return Mono.just(user.toBuilder()
                    .id(user.getId())
                    .apiId(user.getApiId())
                    .firstName(user.getFirstName().toUpperCase())
                    .lastName(user.getLastName().toUpperCase())
                    .email(user.getEmail().toUpperCase())
                    .build()
            );
        }catch (Exception e) {
            logger.error("Error converting message body to user", e);
            throw new TechnicalException(TechnicalMessage.ERROR_MAPPING);
        }

    }

}
