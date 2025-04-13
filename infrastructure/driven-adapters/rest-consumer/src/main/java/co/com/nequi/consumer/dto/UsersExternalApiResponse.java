package co.com.nequi.consumer.dto;

public record UsersExternalApiResponse(
        Long id,
        String email,
        String first_name,
        String last_name,
        String avatar)
{

}
