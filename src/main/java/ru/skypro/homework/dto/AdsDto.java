package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDto {

    private Long pk;
    private final User author;
    private final String title;
    private final byte[] image;
    private final int price;
    private String description;
}
