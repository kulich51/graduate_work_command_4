package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

@Mapper
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "pk", target = "id")
    Ads createAdsToAds(CreateAds ads);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "ads.image.id", target = "image")
    @Mapping(source = "ads.author.id", target = "author")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "image", target = "image.id")
    @Mapping(source = "author", target = "author.id")
    Ads adsDtoToAds(AdsDto ads);

    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}
