package ru.grigorii.NauJava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.grigorii.NauJava.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Конфигурация данных о приложении, заглушки БД и Swagger
 */
@Configuration
public class GeneralConfig
{
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public String getAppName()
    {
        return appName;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    @Bean
    public Map<Long, User> userContainer()
    {
        return new HashMap<>();
    }

    @Bean
    public OpenAPI calendarOpenAPI()
    {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Base API Path")
                ))
                .info(new Info()
                        .title("Calendar API")
                        .description("REST API включает в себя как кастомные методы, так и сгенерированные с " +
                                "помощью Spring REST и HATEOAS")
                        .version("1.0.0"));
    }
}
