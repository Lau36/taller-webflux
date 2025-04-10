package co.com.nequi.redis.template.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheEntity implements Serializable {
    @Id
    private Long id;
    private Long apiId;
    private String firstName;
    private String lastName;
    private String email;
}
