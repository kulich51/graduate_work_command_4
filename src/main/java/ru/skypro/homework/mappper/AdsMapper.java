package ru.skypro.homework.mappper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;

@Mapper
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    Ads createAdsToAds(CreateAds ads);

    AdsDto adsToAdsDto(Ads ads);


}
