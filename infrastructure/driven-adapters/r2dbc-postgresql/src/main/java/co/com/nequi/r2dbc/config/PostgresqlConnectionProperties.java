package co.com.nequi.r2dbc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@AllArgsConstructor
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "adapters.r2dbc")
public class PostgresqlConnectionProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    private String schema;
    private int maxPoolSize;
    private int maxInitialSize;
    private int maxIdleTime;
}
