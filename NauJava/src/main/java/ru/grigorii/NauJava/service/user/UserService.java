package ru.grigorii.NauJava.service.user;

import ru.grigorii.NauJava.dto.user.RegisterUserDto;
import ru.grigorii.NauJava.dto.user.UpdateUserDetailsDto;
import ru.grigorii.NauJava.dto.user.UpdateUserEmailDto;
import ru.grigorii.NauJava.dto.user.UpdateUserPasswordDto;
import ru.grigorii.NauJava.entity.User;

import java.util.List;

public interface UserService
{
    Long register(RegisterUserDto dto);
    User findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    void updateDetails(UpdateUserDetailsDto dto);
    void updateEmail(UpdateUserEmailDto dto);
    void updatePassword(UpdateUserPasswordDto dto);
}
