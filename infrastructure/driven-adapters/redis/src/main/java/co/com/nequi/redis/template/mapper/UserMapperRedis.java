package co.com.nequi.redis.template.mapper;

import co.com.nequi.model.user.User;
import co.com.nequi.redis.template.entity.UserCacheEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapperRedis {
    UserCacheEntity toCacheEntity(User user);
    User toUser(UserCacheEntity cacheEntity);
}
