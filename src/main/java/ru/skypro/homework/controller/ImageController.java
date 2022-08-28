package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AdsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    private final AdsService adsService;

    public ImageController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getAdsAvatar(@PathVariable("id") Long id) {

        return adsService.getImage(id);
    }
}
