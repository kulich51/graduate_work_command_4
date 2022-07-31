package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public ResponseWrapper<User> getAll() {
        return null;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean changePassword(NewPassword newPassword) {
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }
}
