package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.FullAds;

@Mapper
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "pk", target = "id")
    Ads createAdsToAds(CreateAds ads);

    @Mapping(source = "id", target = "pk")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(source = "id", target = "pk")
    FullAdsDto fullAdsToFullAdsDto(FullAds fullAds);

    @Mapping(source = "pk", target = "id")
    Ads adsDtoToAds(AdsDto ads);
}
