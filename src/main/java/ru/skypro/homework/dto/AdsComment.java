package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdsComment {

    private final Date createdAt;
    private final Long author;
    private final Long pk;
    private final String text;
}
