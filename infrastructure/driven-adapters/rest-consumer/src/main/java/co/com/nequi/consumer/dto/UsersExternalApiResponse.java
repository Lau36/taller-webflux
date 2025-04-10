package co.com.nequi.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UsersExternalApiResponse(
        Long id,
        String email,
        String first_name,
        String last_name,
        String avatar)
{

}
