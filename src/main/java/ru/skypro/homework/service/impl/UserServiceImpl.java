package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.exception.NullEmailException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Collection<User> getAll() {

        Collection<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream()
                .map(userProfile -> UserMapper.INSTANCE.userProfileToUser(userProfile))
                .collect(Collectors.toSet());
    }

    @Override
    public User update(User user) {

        checkEmailNotNull(user);
        UserProfile userProfile = UserMapper.INSTANCE.UserToUserProfile(user);
        return UserMapper
                .INSTANCE
                .userProfileToUser(userProfileRepository.save(userProfile));
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

    private void checkEmailNotNull(User user) {

        if (user.getEmail() == null) {
            throw new NullEmailException();
        }
    }
}
