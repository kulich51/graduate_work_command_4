package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.CommentNotFoundException;
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
    public Collection<AdsComment> getAdsComments(Long adsId) {

        Collection<Comment> comments = commentRepository.getByAdsId(adsId);
        return comments.stream()
                .map(CommentMapper.INSTANCE::commentToAdsComment)
                .collect(Collectors.toSet());
    }

    @Override
    public AdsComment addComment(Long adsId, AdsComment adsComment) {

        Comment comment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
        commentRepository.save(comment);
        return mapToAdsComment(comment);
    }

    @Override
    public void deleteComment(Long adsId, Long commentId) {
        commentRepository.deleteByAdsIdAndId(adsId, commentId);
    }

    @Override
    public AdsComment getAdsComment(Long adsId, Long commentId) {

        Comment comment = commentRepository.getByAdsIdAndId(adsId, commentId);
        checkNullComment(comment);
        return mapToAdsComment(comment);
    }

    @Override
    public AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment adsComment) {

        Comment oldComment = commentRepository.getByAdsIdAndId(adsId, commentId);
        if (oldComment != null) {

            Comment newComment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
            newComment.setId(oldComment.getId());
            return mapToAdsComment(commentRepository.save(newComment));
        }
        throw new CommentNotFoundException();
    }

    @Override
    public FullAds getFullAds(Long adsId) {
        return null;
    }

    @Override
    public AdsDto updateAds(Long adsId, AdsDto updatedAds) {
        return null;
    }

    private AdsComment mapToAdsComment(Comment comment) {

        return CommentMapper
                .INSTANCE.commentToAdsComment(comment);
    }

    private void checkNullComment(Comment comment) {

        if (comment == null) {
            throw new CommentNotFoundException();
        }
    }
}
