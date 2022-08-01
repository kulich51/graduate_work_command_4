package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds {

    private final String image;
    private final Long author;
    private final int price;
    private final Long pk;
    private final String title;
}
