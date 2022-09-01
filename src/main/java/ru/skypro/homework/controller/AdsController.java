package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Объявления", description = "Методы работы с объявлениями.")
public class AdsController {

    private final AdsService adsService;

    @Operation(tags = {"Объявления"}, summary = "Сумма объявлений", description = "Возвращает все объявления.")
    @GetMapping
    ResponseEntity<ResponseWrapper<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAds(title));
        return ResponseEntity.ok(ads);
    }

    @Operation(tags = {"Объявления"}, summary = "Создание объявлений", description = "Возвращает создание объявлений с картинкой.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<AdsDto> createAds(Authentication authentication,
                                            @RequestPart("properties") @Valid @NotNull @NotBlank CreateAds ads,
                                            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile photo) {
        System.out.println("createAds");
        return ResponseEntity.ok(adsService.save(ads, authentication.getName(), photo));
    }

    @Operation(tags = {"Объявления"}, summary = "Получение объявлений", description = "Возвращает объявление по имени пользователя.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/me")
    ResponseEntity<ResponseWrapper<AdsDto>> getAdsMe(Authentication authentication) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAdsByUser(authentication.getName()));
        return ResponseEntity.ok(ads);
    }

    @Operation(tags = {"Объявления"}, summary = "Комментарии к объявлению", description = "Получить все комментарии к объявлению по id.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{ad_pk}/comments")
    ResponseEntity<ResponseWrapper<AdsComment>> getAdsComments(@PathVariable(value = "ad_pk") Long adsId) {
        ResponseWrapper<AdsComment> ads = new ResponseWrapper<>(adsService.getAdsComments(adsId));
        return ResponseEntity.ok(ads);
    }

    @Operation(tags = {"Объявления"}, summary = "Новый комментарий", description = "Создать новый коментарий к объявлению.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/{ad_pk}/comments")
    ResponseEntity<AdsComment> addAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @RequestBody AdsComment comment,
                                             Authentication authentication) {
        comment.setPk(adsId);
        return ResponseEntity.ok(adsService.addComment(adsId, comment, authentication));
    }

    @Operation(tags = {"Объявления"}, summary = "Удаление комментариев", description = "Удаление комментариев  к объявлению по id (объявления/комментария).")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    ResponseEntity<?> deleteAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                       @PathVariable(value = "id") Long commentId,
                                       Authentication authentication) {
        adsService.deleteComment(adsId, commentId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(tags = {"Объявления"}, summary = "Получить комментарий", description = "Получить комментарий к объявлению по id(объявления / комментария).")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{ad_pk}/comments/{id}")
    ResponseEntity<AdsComment> getAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                             @PathVariable(value = "id") Long commentId) {
        return ResponseEntity.ok(adsService.getAdsComment(adsId, commentId));
    }

    @Operation(tags = {"Объявления"}, summary = "Изминение комментария", description = "Изминение комментария по id ( объявления/комментария).")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/{ad_pk}/comments/{id}")
    ResponseEntity<AdsComment> updateAdsComment(@PathVariable(value = "ad_pk") Long adsId,
                                                @PathVariable(value = "id") Long commentId,
                                                @RequestBody AdsComment adsComment,
                                                Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAdsComment(adsId, commentId, adsComment, authentication));
    }

    @Operation(tags = {"Объявления"}, summary = "Удаление объявлений", description = "Удаление объявлений по id.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> removeAds(@PathVariable Long id, Authentication authentication) {
        adsService.removeAds(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(tags = {"Объявления"}, summary = "Список объявлений", description = "Получить список всех объявлений по id.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<FullAdsDto> getFullAds(@PathVariable Long id) {
        return ResponseEntity.ok(adsService.getFullAds(id));
    }

    @Operation(tags = {"Объявления"}, summary = "Изминение объявлений", description = "Изминение объявлений по id.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<AdsDto> updateAds(@PathVariable Long id,
                                     @RequestBody AdsDto updatedAds,
                                     Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAds(id, updatedAds, authentication));
    }
}
