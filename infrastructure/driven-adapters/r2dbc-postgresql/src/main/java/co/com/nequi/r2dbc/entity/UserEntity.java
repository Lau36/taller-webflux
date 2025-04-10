package co.com.nequi.r2dbc.entity;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("public.users")
public class UserEntity {

    @Id
    private Long id;

    @Nonnull
    @Column("apiId")
    private Long apiId;

    @Nonnull
    @Column("first_name")
    private String firstName;

    @Nonnull
    @Column("last_name")
    private String lastName;

    @Nonnull
    @Column("email")
    private String email;

}
