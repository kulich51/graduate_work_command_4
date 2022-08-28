package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.UserProfile;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageRepository imageRepository;

    public AdsServiceImpl(CommentRepository commentRepository, AdsRepository adsRepository, UserProfileRepository userProfileRepository, ImageRepository imageRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userProfileRepository = userProfileRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Collection<AdsDto> getAds(String title) {

        title = checkNullTitle(title);
        Collection<Ads> ads = adsRepository.findByTitleContainsOrderByTitle(title);
        return AdsMapper.INSTANCE.adsCollectionToAdsDto(ads);

    }

    private String checkNullTitle(String title) {
        if (title == null) {
            return "";
        }
        return title;
    }

    @Override
    public Collection<AdsDto> getAdsByUser(String email) {

        Long authorId = userProfileRepository.getUserProfileId(email);
        Collection<Ads> ads = adsRepository.findByAuthorId(authorId);
        return AdsMapper.INSTANCE.adsCollectionToAdsDto(ads);
    }

    @Override
    public AdsDto save(CreateAds ads, String email, MultipartFile photo) {

        Image savedImage = saveImage(photo);
        logger.info("Photo have been saved");

        Ads newAds = AdsMapper.INSTANCE.createAdsToAds(ads);
        newAds.setAuthor(userProfileRepository.findByEmail(email));
        newAds.setImage(savedImage);
        logger.info("Save ads: " + newAds);
        return AdsMapper
                .INSTANCE
                .adsToAdsDto(adsRepository.save(newAds));
    }

    private Image saveImage(MultipartFile photo) {
        Image image = new Image();
        try {
            image.setData(photo.getBytes());
            image.setFileSize(photo.getBytes().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setMediaType(photo.getContentType());
        logger.info("saveImage: " + image.toString());
        return imageRepository.save(image);
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

        checkCommentAccess(adsId, commentId, authentication);
        commentRepository.deleteByAdsIdAndId(adsId, commentId);
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

            checkCommentAccess(adsId, commentId, authentication);
            Comment newComment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);
            newComment.setId(commentId);
            newComment.setAdsId(adsId);
            return mapToAdsComment(commentRepository.save(newComment));
        }
        throw new CommentNotFoundException();
    }

    @Override
    public void removeAds(Long adsId, Authentication authentication) {

        checkAdsAccess(adsId, authentication);
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
    public AdsDto updateAds(AdsDto updatedAds, Authentication authentication) {

        if (adsRepository.existsById(updatedAds.getPk())) {
            checkAdsAccess(updatedAds.getPk(), authentication);
            Ads newAds = AdsMapper.INSTANCE.adsDtoToAds(updatedAds);
            return AdsMapper
                    .INSTANCE
                    .adsToAdsDto(adsRepository.save(newAds));
        }
        throw  new AdsNotFoundException();
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
        fullAds.setImage(ads.getImage().getId().toString());
        fullAds.setPrice(ads.getPrice());
        fullAds.setDescription(ads.getDescription());
        fullAds.setEmail(user.getEmail());
        fullAds.setAuthorFirstName(user.getFirstName());
        fullAds.setAuthorLastName(user.getLastName());
        fullAds.setPhone(user.getPhone());
        return fullAds;
    }

    private void checkCommentAccess(Long adsId, Long commentId, Authentication authentication) {

        Long userIdFromComments = commentRepository.getUserProfileId(adsId, commentId);
        Long userIdFromUserProfiles = userProfileRepository.getUserProfileId(authentication.getName());
        boolean isNotEqualsId = userIdFromComments != userIdFromUserProfiles;
        checkAccess(isNotEqualsId, authentication);
    }

    private void checkAdsAccess(Long adsId, Authentication authentication) {

        Long userIdFromAds = adsRepository.getUserProfileId(adsId);
        Long userIdFromUserProfiles = userProfileRepository.getUserProfileId(authentication.getName());
        boolean isNotEqualsId = userIdFromAds != userIdFromUserProfiles;
        checkAccess(isNotEqualsId, authentication);
    }

    private void checkAccess(boolean isNotEqualsId, Authentication authentication) {

        Boolean noAdminRoots = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) == false;

        if (isNotEqualsId && noAdminRoots) {
            throw new AccessDeniedException();
        }
    }
}
