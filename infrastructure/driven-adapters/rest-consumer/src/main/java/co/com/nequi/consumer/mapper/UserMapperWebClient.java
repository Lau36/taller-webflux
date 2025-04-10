package co.com.nequi.consumer.mapper;

import co.com.nequi.consumer.dto.Data;
import co.com.nequi.consumer.dto.UsersExternalApiResponse;
import co.com.nequi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapperWebClient {
    @Mapping(source = "data.id", target = "apiId")
    @Mapping(source = "data.first_name", target = "firstName")
    @Mapping(source = "data.last_name", target = "lastName")
    @Mapping(source = "data.email", target = "email")
    User toUser(Data data);
}
