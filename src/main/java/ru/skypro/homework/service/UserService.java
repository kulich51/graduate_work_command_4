package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;

public interface UserService {

    ResponseWrapper<User> getAll();
    boolean save(User user);
    boolean changePassword(NewPassword newPassword);
}
