package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    @GetMapping
    ResponseEntity<ResponseWrapper<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAds(title));
        return ResponseEntity.ok(ads);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<AdsDto> createAds(Authentication authentication,
                                            @RequestPart("properties") @Valid @NotNull @NotBlank CreateAds ads,
                                            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile photo) {

        System.out.println("createAds");
        return ResponseEntity.ok(adsService.save(ads, authentication.getName(), photo));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/me")
    ResponseEntity<ResponseWrapper<AdsDto>> getAdsMe(Authentication authentication) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAdsByUser(authentication.getName()));
        return ResponseEntity.ok(ads);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{ad_pk}/comments")
    ResponseEntity<ResponseWrapper<AdsComment>> getAdsComments(@PathVariable(value = "ad_pk") Long adsId) {

        ResponseWrapper<AdsComment> ads = new ResponseWrapper<>(adsService.getAdsComments(adsId));
        return ResponseEntity.ok(ads);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/{ad_pk}/comments")
    ResponseEntity<AdsComment> addAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @RequestBody AdsComment comment,
                                             Authentication authentication) {

        comment.setPk(adsId);
        return ResponseEntity.ok(adsService.addComment(adsId, comment, authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    ResponseEntity<?> deleteAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                       @PathVariable(value = "id") Long commentId,
                                       Authentication authentication) {
        adsService.deleteComment(adsId, commentId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{ad_pk}/comments/{id}")
    ResponseEntity<AdsComment> getAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @PathVariable(value = "id") Long commentId) {

        return ResponseEntity.ok(adsService.getAdsComment(adsId, commentId));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/{ad_pk}/comments/{id}")
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
        return ResponseEntity.ok(adsService.updateAds(id, updatedAds, authentication));
    }
}
