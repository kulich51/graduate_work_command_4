package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.mappper.AdsMapper;
import ru.skypro.homework.mappper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;

    public AdsServiceImpl(CommentRepository commentRepository, AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
    }

    @Override
    public Collection<AdsDto> getAllAds() {

        Collection<Ads> ads = adsRepository.findAll();
        return ads.stream()
                .map(AdsMapper.INSTANCE::adsToAdsDto)
                .collect(Collectors.toSet());
    }

    @Override
    public AdsDto save(CreateAds ads) {

        Ads newAds = adsRepository.save(AdsMapper.INSTANCE.createAdsToAds(ads));
        return AdsMapper.INSTANCE.adsToAdsDto(newAds);
    }


    @Override
    public ResponseWrapper<AdsComment> getAdsComments(Long adsId) {
        return null;
    }

    @Override
    public AdsComment addComment(Long adsId, AdsComment adsComment) {

        Comment comment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
        commentRepository.save(comment);
        return CommentMapper
                .INSTANCE.commentToAdsComment(comment);
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
    public AdsDto updateAds(Long adsId, AdsDto updatedAds) {
        return null;
    }
}
