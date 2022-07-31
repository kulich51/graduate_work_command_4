package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class User {

    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Long id;
    private final String email;
}
