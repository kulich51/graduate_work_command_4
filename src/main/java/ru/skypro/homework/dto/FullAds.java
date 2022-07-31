package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class FullAds {

    private final String image;
    private final String authorLastName;
    private final String authorFirstName;
    private final String phone;
    private final int price;
    private final String description;
    private final Long pk;
    private final String title;
    private final String email;
}
