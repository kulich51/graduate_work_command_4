package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private String email;
}
