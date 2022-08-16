package ru.skypro.homework.service;

import ru.skypro.homework.dto.*;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAds(String title);
    AdsDto save(CreateAds ads);
    Collection<AdsComment> getAdsComments(Long adsId);
    AdsComment addComment(Long adsId, AdsComment adsComment);
    AdsComment getAdsComment(Long adsId, Long commentId);
    void deleteComment(Long adsId, Long commentId);
    AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment);
    void removeAds(Long adsId);
    FullAdsDto getFullAds(Long adsId);
    AdsDto updateAds(AdsDto updatedAds);
}
