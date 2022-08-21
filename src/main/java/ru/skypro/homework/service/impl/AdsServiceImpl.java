package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserProfileRepository userProfileRepository;

    public AdsServiceImpl(CommentRepository commentRepository, AdsRepository adsRepository, UserProfileRepository userProfileRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Collection<AdsDto> getAds(String title) {

        title = checkNullTitle(title);
        Collection<Ads> ads = adsRepository.findByTitleContainsOrderByTitle(title);
        logger.info("AdsServiceImpl.getAds: ".concat(ads.toString()));
        return ads.stream()
                .map(AdsMapper.INSTANCE::adsToAdsDto)
                .collect(Collectors.toSet());
    }

    private String checkNullTitle(String title) {
        if (title == null) {
            return "";
        }
        return title;
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
    public void deleteComment(Long adsId, Long commentId, Authentication authentication) {

        checkUserAccess(adsId, commentId, authentication);
        commentRepository.deleteByAdsIdAndId(adsId, commentId);
        logger.info("Comment delete successful");
    }

    @Override
    public AdsComment getAdsComment(Long adsId, Long commentId) {

        Comment comment = commentRepository.getByAdsIdAndId(adsId, commentId).get();
        checkNullComment(comment);
        return mapToAdsComment(comment);
    }

    @Override
    public AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment adsComment, Authentication authentication) {

        Comment oldComment = commentRepository.getByAdsIdAndId(adsId, commentId).get();
        if (oldComment != null) {

            checkUserAccess(adsId, commentId, authentication);
            Comment newComment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
            newComment.setId(commentId);
            newComment.setAdsId(adsId);
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

    private void checkUserAccess(Long adsId, Long commentId, Authentication authentication) {

        Long userIdFromComments = commentRepository.getUserProfileId(adsId, commentId);
        Long userIdFromUserProfiles = userProfileRepository.getUserProfileId(authentication.getName());

        Boolean noAdminRoots = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) == false;

        if (userIdFromComments != userIdFromUserProfiles && noAdminRoots) {
            throw new AccessDeniedException();
        }
    }
}
