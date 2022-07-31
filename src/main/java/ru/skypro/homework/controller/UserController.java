package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping
    ResponseEntity<?> addNewUser(@RequestBody RegisterReq user) {

        Role role = user.getRole() == null ? USER : user.getRole();
        if (authService.register(user, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("me")
    ResponseEntity<ResponseWrapper<User>> getAllUsers() {

        return ResponseEntity.ok(userService.getAll());
    }

    @PatchMapping("me")
    ResponseEntity<?> updateUser(@RequestBody User user) {

        if (userService.save(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("set_password")
    ResponseEntity<?> changePassword(@RequestBody NewPassword newPassword) {

        if (userService.changePassword(newPassword)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
