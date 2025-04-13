package co.com.nequi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("local")
@ExtendWith(MockitoExtension.class)
class UserIntegrationTest {

    @Autowired
    private ReactiveRedisTemplate<String, String> template;

    @Test
    void putUserInRedis() {
        String key = "user:id:1";
        String value = "test-value";
        StepVerifier.create(template.opsForValue().set(key, value)
                        .then(template.opsForValue().get(key)))
                        .expectNext(value).verifyComplete();
    }
}
