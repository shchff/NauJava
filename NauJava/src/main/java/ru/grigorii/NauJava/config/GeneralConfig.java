package ru.grigorii.NauJava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.grigorii.NauJava.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Конфигурация данных о приложении, заглушки БД и Swagger
 */
@Configuration
@EnableWebSecurity
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
                                .url("http://localhost:8080/api")
                                .description("Base API Path")
                ))
                .info(new Info()
                        .title("Calendar API")
                        .description("REST API включает в себя как кастомные методы, так и сгенерированные с " +
                                "помощью Spring REST и HATEOAS")
                        .version("1.0.0"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
    {
        return security
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**")
                        .permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
