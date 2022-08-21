package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
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
    public Collection<AdsDto> getAds(String title) {

        Collection<Ads> ads = adsRepository.findByTitleContainsOrderByTitle(title);
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

        Comment comment = commentRepository.getByAdsIdAndId(adsId, commentId).get();
        checkNullComment(comment);
        return mapToAdsComment(comment);
    }

    @Override
    public AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment adsComment) {

        Comment oldComment = commentRepository.getByAdsIdAndId(adsId, commentId).get();
        if (oldComment != null) {

            Comment newComment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
            newComment.setId(oldComment.getId());
            return mapToAdsComment(commentRepository.save(newComment));
        }
        throw new CommentNotFoundException();
    }

    @Override
    public void removeAds(Long adsId) {

        commentRepository.deleteAllByAdsId(adsId);
        adsRepository.deleteAllById(adsId);
    }

    @Override
    public FullAdsDto getFullAds(Long adsId) {

        Ads ads = adsRepository.findById(adsId).get();
        UserProfile user = ads.getAuthor();
        return getFullAds(ads, user);
    }

    @Override
    public AdsDto updateAds(AdsDto updatedAds) {
        Ads ads = AdsMapper.INSTANCE.adsDtoToAds(updatedAds);

        return AdsMapper
                .INSTANCE
                .adsToAdsDto(adsRepository.save(ads));
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

    private FullAdsDto getFullAds(Ads ads, UserProfile user) {

        FullAdsDto fullAds = new FullAdsDto();
        fullAds.setPk(ads.getId());
        fullAds.setTitle(ads.getTitle());
        fullAds.setImage(ads.getImage());
        fullAds.setPrice(ads.getPrice());
        fullAds.setDescription(ads.getDescription());
        fullAds.setEmail(user.getEmail());
        fullAds.setAuthorFirstName(user.getFirstName());
        fullAds.setAuthorLastName(user.getLastName());
        fullAds.setPhone(user.getPhone());
        return fullAds;
    }
}
