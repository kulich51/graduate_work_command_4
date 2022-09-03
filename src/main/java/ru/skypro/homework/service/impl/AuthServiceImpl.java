package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserProfileRepository userProfileRepository;

    public AuthServiceImpl(UserDetailsManager manager, UserProfileRepository userProfileRepository) {
        this.manager = manager;
        this.userProfileRepository = userProfileRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    /**
     * Получить статус об авторизации пользователя
     * @param userName логин пользователя (email)
     * @param password пароль пользователя
     * @return false, если пользователя не существует, либо true
     */
    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }

        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    /**
     * Получить статус о регистрации нового пользователя
     * @param registerReq сущность с данными пользователя с формы регистрации
     * @param role        роль позльзователя USER / ADMIN
     * @return false, если пользователь существует в БД, true, если не существует в БД
     */
    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }

        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );

        UserProfile userProfile = UserMapper.INSTANCE.registerReqToUserProfile(registerReq);
        userProfileRepository.save(userProfile);
        return true;
    }

    /**
     * Получить стаус об изменении пароля пользователя
     * @param newPassword объект с новым и старым паролем пользователя
     * @param username    логин пользователя (email)
     * @return true, если пароль изменен, либо false
     */
    @Override
    public boolean changePassword(NewPassword newPassword, String username) {

        if (login(username, newPassword.getCurrentPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            manager.changePassword(
                    newPassword.getCurrentPassword(),
                    "{bcrypt}" + encoder.encode(newPassword.getNewPassword()));
            return true;
        }
        return false;
    }
}
