package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapper;

public interface AdsService {

    ResponseWrapper<Ads> getAllAds();
    Ads save(CreateAds ads);
    ResponseWrapper<AdsComment> getAdsComments(Long adsId);
    AdsComment addAdsComment(Long adsId, AdsComment comment);
}
