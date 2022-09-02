package ru.skypro.homework.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsComment {

    private final LocalDateTime createdAt;
    private final Long author;
    private Long pk;
    private final String text;
}
