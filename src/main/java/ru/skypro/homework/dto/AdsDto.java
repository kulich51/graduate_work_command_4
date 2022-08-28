package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDto {

    private Long pk;
    private final Long author;
    private final String title;
    private final String image;
    private final int price;
    private String description;
}
