package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.AdsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    @GetMapping
    ResponseEntity<ResponseWrapper<Ads>> getAllAds() {

        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping
    ResponseEntity<Ads> createAds(@RequestBody CreateAds ads) {

        return ResponseEntity.ok(adsService.save(ads));
    }
}
