package co.com.nequi.r2dbc.mapper;

import co.com.nequi.model.user.User;
import co.com.nequi.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserEntity user);
    UserEntity toUserEntity(User user);
}
