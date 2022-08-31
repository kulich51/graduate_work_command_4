package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("me")
    ResponseEntity<User> getUser(Authentication authentication) {

        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("me")
    ResponseEntity<User> updateUser(@RequestBody User user, Authentication authentication) {

        user.setEmail(authentication.getName());
        return ResponseEntity.ok(userService.update(user));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("set_password")
    ResponseEntity<NewPassword> changePassword(@RequestBody NewPassword newPassword, Authentication authentication) {

        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(newPassword);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

}
