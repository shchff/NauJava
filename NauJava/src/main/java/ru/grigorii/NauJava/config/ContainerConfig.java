package ru.grigorii.NauJava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.grigorii.NauJava.entity.User;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ContainerConfig
{
    @Bean
    public Map<Long, User> userContainer()
    {
        return new HashMap<>();
    }
}
