package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.exception.NullEmailException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public User getUser(String email) {

        UserProfile user = userProfileRepository.findByEmail(email);
        return UserMapper
                .INSTANCE
                .userProfileToUser(user);
    }

    @Override
    public User update(User user) {

        checkEmailNotNull(user);
        UserProfile userProfile = UserMapper.INSTANCE.UserToUserProfile(user);
        userProfile.setId(getUserProfileId(userProfile.getEmail()));
        return UserMapper
                .INSTANCE
                .userProfileToUser(userProfileRepository.save(userProfile));
    }

    @Override
    public User getUserById(Long id) {

        if (userProfileRepository.existsById(id)) {
            UserProfile userProfile = userProfileRepository.findById(id).get();
            return UserMapper.INSTANCE.userProfileToUser(userProfile);
        }
        throw new UserNotFoundException();
    }

    private void checkEmailNotNull(User user) {

        if (user.getEmail() == null) {
            throw new NullEmailException();
        }
    }

    private Long getUserProfileId(String email) {

        UserProfile user = userProfileRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
