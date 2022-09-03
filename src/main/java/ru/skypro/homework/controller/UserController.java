package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользователи", description = "Методы работы с пользователем.")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(tags = {"Пользователи"}, summary = "Информация о пользователе",
            description = "Получить информацию о пользователе по email")
    @GetMapping("me")
    ResponseEntity<User> getUser(Authentication authentication) {

        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @Operation(tags = {"Пользователи"}, summary = "Изменение пользователя",
            description = "Изменить информацию о пользователе")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("me")
    ResponseEntity<User> updateUser(@RequestBody User user, Authentication authentication) {

        user.setEmail(authentication.getName());
        return ResponseEntity.ok(userService.update(user));
    }

    @Operation(tags = {"Пользователи"}, summary = "Изменение пароля",
            description = "Изменить старый пароль пользователя на новый")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("set_password")
    ResponseEntity<NewPassword> changePassword(@RequestBody NewPassword newPassword, Authentication authentication) {

        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(newPassword);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(tags = {"Пользователи"}, summary = "Получение пользователя",
            description = "Получить пользователя по id в БД")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
