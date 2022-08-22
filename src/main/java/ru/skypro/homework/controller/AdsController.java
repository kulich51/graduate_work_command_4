package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    @GetMapping
    ResponseEntity<ResponseWrapper<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
//        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAds(title));
//        return ResponseEntity.ok(ads);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<AdsDto> createAds(@RequestBody CreateAds ads) {

        return ResponseEntity.ok(adsService.save(ads));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("me")
    ResponseEntity<ResponseWrapper<AdsDto>> getAdsMe(@RequestParam(required = false) String title) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAds(title));
        return ResponseEntity.ok(ads);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("{ad_pk}/comment")
    ResponseEntity<ResponseWrapper<AdsComment>> getAdsComments(@PathVariable(value = "ad_pk") Long adsId) {

        ResponseWrapper<AdsComment> ads = new ResponseWrapper<>(adsService.getAdsComments(adsId));
        return ResponseEntity.ok(ads);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("{ad_pk}/comment")
    ResponseEntity<AdsComment> addAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @RequestBody AdsComment comment) {

        comment.setPk(adsId);
        return ResponseEntity.ok(adsService.addComment(adsId, comment));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ad_pk}/comment/{id}")
    ResponseEntity<?> deleteAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                       @PathVariable(value = "id") Long commentId,
                                       Authentication authentication) {
        adsService.deleteComment(adsId, commentId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{ad_pk}/comment/{id}")
    ResponseEntity<AdsComment> getAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @PathVariable(value = "id") Long commentId) {

        return ResponseEntity.ok(adsService.getAdsComment(adsId, commentId));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/{ad_pk}/comment/{id}")
    ResponseEntity<AdsComment> updateAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                                @PathVariable(value = "id") Long commentId,
                                                @RequestBody AdsComment adsComment,
                                                Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAdsComment(adsId, commentId, adsComment, authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> removeAds(@PathVariable Long id, Authentication authentication) {

        adsService.removeAds(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<FullAdsDto> getFullAds(@PathVariable Long id) {

        return ResponseEntity.ok(adsService.getFullAds(id));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<AdsDto> updateAds(@PathVariable Long id,
                                     @RequestBody AdsDto updatedAds,
                                     Authentication authentication) {
        updatedAds.setPk(id);
        return ResponseEntity.ok(adsService.updateAds(updatedAds, authentication));
    }
}
