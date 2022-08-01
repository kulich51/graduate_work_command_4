package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.*;

public interface AdsService {

    ResponseWrapper<Ads> getAllAds();
    Ads save(CreateAds ads);
    ResponseWrapper<AdsComment> getAdsComments(Long adsId);
    AdsComment addAdsComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment);
    FullAds getFullAds(Long adsId);
    Ads updateAds(Long adsId, Ads updatedAds);
}
