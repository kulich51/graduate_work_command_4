package ru.skypro.homework.service;

import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAllAds();
    AdsDto save(CreateAds ads);
    ResponseWrapper<AdsComment> getAdsComments(Long adsId);
    AdsComment addAdsComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment);
    FullAds getFullAds(Long adsId);
    AdsDto updateAds(Long adsId, AdsDto updatedAds);
}
