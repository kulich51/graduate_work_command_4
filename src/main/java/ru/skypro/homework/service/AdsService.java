package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapper;

public interface AdsService {

    ResponseWrapper<Ads> getAllAds();
    Ads save(CreateAds ads);
}
