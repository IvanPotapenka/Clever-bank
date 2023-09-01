package by.potapenko.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public final class PropertiesApi {
    private final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    @SneakyThrows
    private static void loadProperties() {
        try (InputStream inputStream = PropertiesApi.class.getClassLoader().getResourceAsStream(
                "datasource.properties")) {
            PROPERTIES.load(inputStream);
        }
    }
}
