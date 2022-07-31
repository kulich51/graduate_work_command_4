package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.AdsComment;
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

    @GetMapping("me")
    ResponseEntity<ResponseWrapper<Ads>> getAdsMe(@RequestParam(required = false) Boolean authenticated,
                                                  @RequestParam(value = "authorities[0].authority", required = false) Boolean authority,
                                                  @RequestParam(required = false) Object credentials,
                                                  @RequestParam(required = false) Object details,
                                                  @RequestParam(required = false) Object principal) {

        return ResponseEntity.ok(adsService.getAllAds());
    }

    @GetMapping("{ad_pk}/comment")
    ResponseEntity<ResponseWrapper<AdsComment>> getAdsComments(@PathVariable(value = "ad_pk") Long adsId) {

        return ResponseEntity.ok(adsService.getAdsComments(adsId));
    }

    @PostMapping("{ad_pk}/comment")
    ResponseEntity<AdsComment> addAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @RequestBody AdsComment comment) {

        return ResponseEntity.ok(adsService.addAdsComment(adsId, comment));
    }

    @DeleteMapping("/ads/{ad_pk}/comment/{id}")
    ResponseEntity<?> deleteAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                       @PathVariable(value = "id") Long commentId) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/ads/{ad_pk}/comment/{id}")
    ResponseEntity<AdsComment> getAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @PathVariable(value = "id") Long commentId) {

        return ResponseEntity.ok(adsService.getAdsComment(adsId, commentId));
    }

    @PatchMapping("/ads/{ad_pk}/comment/{id}")
    ResponseEntity<AdsComment> updateAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                                @PathVariable(value = "id") Long commentId,
                                                @RequestBody AdsComment comment) {
        return ResponseEntity.ok(adsService.updateAdsComment(adsId, commentId, comment));
    }



}
