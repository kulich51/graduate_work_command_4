package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAds(String title);
    AdsDto save(CreateAds ads, String email, MultipartFile photo);
    Collection<AdsComment> getAdsComments(Long adsId);
    AdsComment addComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    void deleteComment(Long adsId, Long commentId, Authentication authentication);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment, Authentication authentication);
    void removeAds(Long adsId, Authentication authentication);
    FullAdsDto getFullAds(Long adsId);
    AdsDto updateAds(AdsDto updatedAds, Authentication authentication);
    Collection<AdsDto> getAdsByUser(String email);
    byte[] getImage(Long id);
}
