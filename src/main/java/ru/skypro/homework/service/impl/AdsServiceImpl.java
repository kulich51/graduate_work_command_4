package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;

@Service
public class AdsServiceImpl implements AdsService {

    @Override
    public ResponseWrapper<Ads> getAllAds() {
        return null;
    }

    @Override
    public Ads save(CreateAds ads) {
        return null;
    }

    @Override
    public ResponseWrapper<AdsComment> getAdsComments(Long adsId) {
        return null;
    }

    @Override
    public AdsComment addAdsComment(Long adsId, AdsComment comment) {
        return null;
    }

    @Override
    public AdsComment getAdsComment(Long adsId, Long commentId) {
        return null;
    }

    @Override
    public AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment comment) {
        return null;
    }

    @Override
    public FullAds getFullAds(Long adsId) {
        return null;
    }

    @Override
    public Ads updateAds(Long adsId, Ads updatedAds) {
        return null;
    }
}
