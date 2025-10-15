package ru.grigorii.NauJava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.grigorii.NauJava.entity.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация данных о приложении и заглушки БД
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
}
