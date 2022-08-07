package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class FullAdsDto {

    private String image;
    private String authorLastName;
    private String authorFirstName;
    private String phone;
    private int price;
//    поля description нет в таблице ads
//    private final String description;
    private Long pk;
    private String title;
    private String email;
}
