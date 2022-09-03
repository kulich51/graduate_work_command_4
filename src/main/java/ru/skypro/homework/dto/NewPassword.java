package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class NewPassword {

    private final String newPassword;
    private final String currentPassword;
}
