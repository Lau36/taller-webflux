package co.com.nequi.api.mapper;

import co.com.nequi.api.response.UserResponse;
import co.com.nequi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapperEntryPoint {
    UserResponse toResponse(User user);
}
