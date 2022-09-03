package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

import java.util.Collection;

public interface UserService {

    User update(User user);
    User getUserById(Long id);
    User getUser(String email);
}
