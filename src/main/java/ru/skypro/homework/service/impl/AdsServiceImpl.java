package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mappper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

@Service
public class AdsServiceImpl implements AdsService {

    private final CommentRepository commentRepository;

    public AdsServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

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
    public AdsComment addAdsComment(Long adsId, AdsComment adsComment) {

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
    public Ads updateAds(Long adsId, Ads updatedAds) {
        return null;
    }
}
