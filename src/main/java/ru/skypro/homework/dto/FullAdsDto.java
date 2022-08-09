package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class FullAdsDto {

    private String image;
    private String authorLastName;
    private String authorFirstName;
    private String phone;
    private int price;
    private String description;
    private Long pk;
    private String title;
    private String email;
}
