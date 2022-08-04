package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;

import java.util.Collection;

public interface UserService {

    Collection<User> getAll();
    User update(User user);
    boolean save(User user);
    boolean changePassword(NewPassword newPassword);
    User getUserById(Long id);
}
