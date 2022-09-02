package ru.skypro.homework.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateAds {

    private final Long pk;
    private final User author;
    private final String title;
    private final int price;
    private final MultipartFile image;
    private final String description;
}
