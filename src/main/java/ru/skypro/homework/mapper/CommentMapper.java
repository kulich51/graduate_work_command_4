package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "pk", target = "adsId")
    Comment adsCommentToComment(AdsComment comment);

    @Mapping(source = "adsId", target = "pk")
    AdsComment commentToAdsComment(Comment comment);
}