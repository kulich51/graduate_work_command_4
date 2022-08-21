package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("me")
    ResponseEntity<ResponseWrapper<User>> getAllUsers() {

        Collection<User> users = userService.getAll();
        return ResponseEntity.ok(new ResponseWrapper<User>(users));
    }

    @PatchMapping("me")
    ResponseEntity<User> updateUser(@RequestBody User user, Authentication authentication) {

        user.setEmail(authentication.getName());
        logger.info("Update user: ".concat(user.toString()));
        return ResponseEntity.ok(userService.update(user));
    }

    @PostMapping("set_password")
    ResponseEntity<NewPassword> changePassword(@RequestBody NewPassword newPassword) {

        if (userService.changePassword(newPassword)) {
            return ResponseEntity.ok(newPassword);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

}
