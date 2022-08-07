package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds {

    private final Long pk;
    private final Long author;
    private final String title;
    private final String image;
    private final int price;
}
