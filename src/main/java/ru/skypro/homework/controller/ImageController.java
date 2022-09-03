package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.service.AdsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Изображения", description = "Метод работы с изображениями.")
public class ImageController {

    private final AdsService adsService;

    public ImageController(AdsService adsService) {
        this.adsService = adsService;
    }

    @Operation(tags = {"Изображения"}, summary = "Получение изображения",
            description = "Получение избражения по id картинки в БД.")
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getAdsAvatar(@PathVariable("id") Long id) {

        return adsService.getImage(id);
    }
}
