package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping
    ResponseEntity<?> addNewUser(@RequestBody RegisterReq user) {

        Role role = user.getRole() == null ? USER : user.getRole();
        if (authService.register(user, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
