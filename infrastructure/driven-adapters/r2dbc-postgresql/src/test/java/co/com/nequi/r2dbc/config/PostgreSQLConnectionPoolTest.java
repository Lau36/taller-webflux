package co.com.nequi.r2dbc.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PostgreSQLConnectionPoolTest {

    @InjectMocks
    private PostgreSQLConnectionPool connectionPool;

    @Mock
    private PostgresqlConnectionProperties properties;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(properties.getHost()).thenReturn("localhost");
        when(properties.getPort()).thenReturn(3306);
        when(properties.getDatabase()).thenReturn("apiUsers");
        when(properties.getUsername()).thenReturn("postgres");
        when(properties.getPassword()).thenReturn("root123");
        when(properties.getMaxPoolSize()).thenReturn(30);
        when(properties.getMaxInitialSize()).thenReturn(20);
        when(properties.getMaxIdleTime()).thenReturn(5);

    }

    @Test
    void getConnectionConfigSuccess() {
        assertNotNull(connectionPool.getConnectionConfig(properties));
    }
}
