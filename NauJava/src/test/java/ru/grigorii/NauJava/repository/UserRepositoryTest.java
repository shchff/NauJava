package ru.grigorii.NauJava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.grigorii.NauJava.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRepository test")
@DataJpaTest
public class UserRepositoryTest
{
    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUp()
    {
        repository.deleteAll();

        user = repository.save(
                User.requiredFields(
                        "grisaserbakov0@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );
    }

    @DisplayName("Should return user by email")
    @Test
    void shouldReturnUserByEmail()
    {
        User userFoundByEmail = repository.findByEmail(user.getEmail()).orElse(null);

        assertNotNull(userFoundByEmail);
        assertEquals(user.getEmail(), userFoundByEmail.getEmail());
    }

    @DisplayName("Should not return any user by email")
    @Test
    void shouldNotReturnAnyUserByEmail()
    {
        User userFoundByEmail = repository.findByEmail("").orElse(null);

        assertNull(userFoundByEmail);
    }
}
