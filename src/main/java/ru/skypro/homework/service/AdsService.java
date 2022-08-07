package ru.skypro.homework.service;

import ru.skypro.homework.dto.*;

public interface AdsService {

    ResponseWrapper<AdsDto> getAllAds();
    AdsDto save(CreateAds ads);
    ResponseWrapper<AdsComment> getAdsComments(Long adsId);
    AdsComment addAdsComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment);
    FullAds getFullAds(Long adsId);
    AdsDto updateAds(Long adsId, AdsDto updatedAds);
}
