package ru.skypro.homework.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phone;
}
