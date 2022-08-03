package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
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
        System.out.println(userProfile);
        userProfileRepository.save(userProfile);

        return true;
    }

    private UserProfile getUserProfile(RegisterReq registerReq) {

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(registerReq.getUsername());
        userProfile.setFirstName(registerReq.getFirstName());
        userProfile.setLastName(registerReq.getLastName());
        userProfile.setPhone(registerReq.getPhone());
        return userProfile;
    }
}
