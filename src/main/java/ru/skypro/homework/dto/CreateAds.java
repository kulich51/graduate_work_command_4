package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds {

    private final Long pk;
    private final User author;
    private final String title;
    private final int price;
}
