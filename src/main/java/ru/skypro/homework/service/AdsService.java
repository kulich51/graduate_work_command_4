package ru.skypro.homework.service;

import ru.skypro.homework.dto.*;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAllAds();
    AdsDto save(CreateAds ads);
    Collection<AdsComment> getAdsComments(Long adsId);
    AdsComment addComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    void deleteComment(Long adsId, Long commentId);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment);
    FullAds getFullAds(Long adsId);
    AdsDto updateAds(Long adsId, AdsDto updatedAds);
}
