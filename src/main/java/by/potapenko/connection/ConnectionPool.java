package by.potapenko.connection;

import by.potapenko.util.PropertiesApi;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;

@UtilityClass
public class ConnectionPool {
    private final DataSource DATA_SOURCE;
    private final String USER_KEY = "db.user";
    private final String PASSWORD_KEY = "db.password";
    private final String URL_KEY = "db.url";
    private final String DRIVER_KEY = "db.driver";
    private final String POOL_SIZE_KEY = "db.pool.size";


    static {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUsername(PropertiesApi.get(USER_KEY));
        poolProperties.setPassword(PropertiesApi.get(PASSWORD_KEY));
        poolProperties.setUrl(PropertiesApi.get(URL_KEY));
        poolProperties.setDriverClassName(PropertiesApi.get(DRIVER_KEY));
        poolProperties.setMaxActive(Integer.parseInt(PropertiesApi.get(POOL_SIZE_KEY)));
        DATA_SOURCE = new DataSource(poolProperties);
    }

    @SneakyThrows
    public Connection open() {
        return DATA_SOURCE.getConnection();
    }
}