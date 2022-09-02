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
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserProfileRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageRepository imageRepository;

    private static String SOURCE_URL = "http://127.0.0.1:8080/";

    public AdsServiceImpl(CommentRepository commentRepository, AdsRepository adsRepository, UserProfileRepository userProfileRepository, ImageRepository imageRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userProfileRepository = userProfileRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Получить коллекцию объявлений по заголовоку
     * @param title заголовок объявлений
     * @return коллекция объявлений, содержащих переданный заголовок
     */
    @Override
    public Collection<AdsDto> getAds(String title) {
        title = checkNullTitle(title);
        Collection<Ads> ads = adsRepository.findByTitleContainsOrderByTitle(title);
        return AdsMapper.INSTANCE.adsCollectionToAdsDto(ads);
    }

    /**
     * Проверить, что заголовок объявления не null
     * @param title заголовок объяления
     * @return заголовок объявления или пустую строку, если заголовок null
     */
    private String checkNullTitle(String title) {
        if (title == null) {
            return "";
        }
        return title;
    }

    /**
     * Получить коллекцию объявлений конкретного пользователя по email
     * @param email email пользователя
     * @return коллекция объявлений, созданных пользователем с переданным email
     */
    @Override
    public Collection<AdsDto> getAdsByUser(String email) {

        Long authorId = userProfileRepository.getUserProfileId(email);
        Collection<Ads> ads = adsRepository.findByAuthorId(authorId);
        return AdsMapper.INSTANCE.adsCollectionToAdsDto(ads);
    }

    /**
     * Сохраненить объявление с изображением в БД
     * @param ads   объявление пользователя
     * @param email email пользователя
     * @param photo изображение из объявления передаваемое пользователем
     * @return сохраненное объявление в БД
     */
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

    /**
     * Сохранить изображение в БД
     * @param photo изображение из объявления передаваемое пользователем
     * @return сохраненное избражение в БД
     */
    private Image saveImage(MultipartFile photo) {
        Image image = new Image();
        try {
            image.setData(photo.getBytes());
            image.setFileSize(photo.getBytes().length);
        } catch (IOException e) {
            logger.info("AdsServiceImpl.saveImage: " + e.toString());
        }
        image.setMediaType(photo.getContentType());
        return imageRepository.save(image);
    }

    /**
     * Получить все комментарии к объявлению по id объявления в БД
     * @param adsId id объявления
     * @return коллекцию комментариев по переданному объявлению
     */
    @Override
    public Collection<AdsComment> getAdsComments(Long adsId) {

        Collection<Comment> comments = commentRepository.getByAdsId(adsId);
        return comments.stream()
                .map(CommentMapper.INSTANCE::commentToAdsComment)
                .collect(Collectors.toSet());
    }

    /**
     * Добавить комментарий к объявлению
     * @param adsId          id объявления
     * @param adsComment     комментарий к объявлению
     * @param authentication сущность с данными по авторизированному пользователю
     * @return сохраненный комментарий из БД
     */
    @Override
    public AdsComment addComment(Long adsId, AdsComment adsComment, Authentication authentication) {

        Long userId = userProfileRepository.getUserProfileId(authentication.getName());

        Comment comment = CommentMapper.INSTANCE.adsCommentToComment(adsComment);

        comment.setAuthor(userId);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
        return mapToAdsComment(comment);
    }

    /**
     * Удалить комментарий
     * @param adsId          id объявления
     * @param commentId      id комментария
     * @param authentication сущность с данными по авторизированному пользователю
     */
    @Override
    public void deleteComment(Long adsId, Long commentId, Authentication authentication) {

        checkCommentAccess(adsId, commentId, authentication);
        commentRepository.deleteByAdsIdAndId(adsId, commentId);
    }

    /**
     * Получить комментарий к объявлению по id объявления и комментария
     * @param adsId     id объявления
     * @param commentId id комментария
     * @return комментарий по заданному id объявления и комментария
     */
    @Override
    public AdsComment getAdsComment(Long adsId, Long commentId) {

        Comment comment = commentRepository.getByAdsIdAndId(adsId, commentId).get();
        checkNullComment(comment);
        return mapToAdsComment(comment);
    }

    /**
     * Измененить комментарий
     * @param adsId          id объявления
     * @param commentId      id комментария
     * @param adsComment     откорректированный комментарий к объявлению
     * @param authentication сущность с данными по авторизированному пользователю
     * @return откорректированный комментарий из БД
     */
    @Override
    public AdsComment updateAdsComment(Long adsId, Long commentId, AdsComment adsComment, Authentication authentication) {

        Comment comment = commentRepository.getByAdsIdAndId(adsId, commentId).orElseThrow(CommentNotFoundException::new);

        checkCommentAccess(adsId, commentId, authentication);
        // У старого комментария меняется только текст. Дата создания не меняется
        comment.setText(adsComment.getText());
        return mapToAdsComment(commentRepository.save(comment));
    }

    /**
     * Удалить объявление по id объявления в БД
     * @param adsId          id объявления
     * @param authentication сущность с данными по авторизированному пользователю
     */
    @Override
    public void removeAds(Long adsId, Authentication authentication) {

        checkAdsAccess(adsId, authentication);
        commentRepository.deleteAllByAdsId(adsId);
        adsRepository.deleteAllById(adsId);
    }

    /**
     * Получить полную информацию об объявлении по id объявления в БД
     * @param adsId id объявления
     * @return полная информация об объявлении
     */
    @Override
    public FullAdsDto getFullAds(Long adsId) {

        Ads ads = adsRepository.findById(adsId).get();
        UserProfile user = ads.getAuthor();
        return getFullAds(ads, user);
    }

    /**
     * Измененить объявлений по id объявления в БД
     * @param id             id объявления
     * @param updatedAds     откорректированное объявление
     * @param authentication сущность с данными по авторизированному пользователю
     * @return откорректированное объявление в БД
     * @throws AdsNotFoundException если объявление не найдено
     */
    @Override
    public AdsDto updateAds(Long id, AdsDto updatedAds, Authentication authentication) {

        Ads oldAds = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        checkAdsAccess(id, authentication);

        // С фронта при корректировке объявления передаются только поля: description, price, title
        // Остальные поля заполняются из сторой записи объявления
        Ads newAds = AdsMapper.INSTANCE.adsDtoToAds(updatedAds);
        checkNewAdsForNullFields(oldAds, newAds);
        newAds.setId(id);
        newAds.setAuthor(oldAds.getAuthor());
        newAds.setImage(oldAds.getImage());
        return AdsMapper
                .INSTANCE
                .adsToAdsDto(adsRepository.save(newAds));
    }

    /**
     * Проверить поля объявления на null
     * @param oldAds старое объявление
     * @param newAds новое объявление
     */
    private void checkNewAdsForNullFields(Ads oldAds, Ads newAds) {

        if (newAds.getTitle() == null) {
            newAds.setTitle(oldAds.getTitle());
        }

        if (newAds.getDescription() == null) {
            newAds.setDescription(oldAds.getDescription());
        }

        if (newAds.getPrice() == 0) {
            newAds.setPrice(oldAds.getPrice());
        }
    }

    /**
     * Получить изображение
     * @param id id объявления
     * @return изображение по заданному объявлению
     */
    @Override
    public byte[] getImage(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return image.getData();
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

    /**
     * Получить полную информацию об объявлении
     * @param ads  объявление
     * @param user пользователь
     * @return полную информацию об объявлении
     */
    private FullAdsDto getFullAds(Ads ads, UserProfile user) {

        FullAdsDto fullAds = new FullAdsDto();
        fullAds.setPk(ads.getId());
        fullAds.setTitle(ads.getTitle());
        fullAds.setImage(SOURCE_URL + ads.getImage().getId().toString());
        fullAds.setPrice(ads.getPrice());
        fullAds.setDescription(ads.getDescription());
        fullAds.setEmail(user.getEmail());
        fullAds.setAuthorFirstName(user.getFirstName());
        fullAds.setAuthorLastName(user.getLastName());
        fullAds.setPhone(user.getPhone());
        return fullAds;
    }

    /**
     * Проверить доступ к комментарием
     * @param adsId          id объявления
     * @param commentId      id комментария
     * @param authentication сущность с данными по авторизированному пользователю
     */
    private void checkCommentAccess(Long adsId, Long commentId, Authentication authentication) {

        Long userIdFromComments = commentRepository.getUserProfileId(adsId, commentId);
        Long userIdFromUserProfiles = userProfileRepository.getUserProfileId(authentication.getName());
        boolean isNotEqualsId = userIdFromComments != userIdFromUserProfiles;
        checkAccess(isNotEqualsId, authentication);
    }

    /**
     * Проверить доступ к объявлениям
     * @param adsId          id объявления
     * @param authentication сущность с данными по авторизированному пользователю
     */
    private void checkAdsAccess(Long adsId, Authentication authentication) {

        Long userIdFromAds = adsRepository.getUserProfileId(adsId);
        Long userIdFromUserProfiles = userProfileRepository.getUserProfileId(authentication.getName());
        boolean isNotEqualsId = userIdFromAds != userIdFromUserProfiles;
        checkAccess(isNotEqualsId, authentication);
    }

    /**
     * Проверить доступ к данным в роли ADMIN
     * @param isNotEqualsId  сравнение ID пользователя
     * @param authentication сущность с данными по авторизированному пользователю
     * @throws AccessDeniedException если пользователь не имеет права доступа к данным
     */
    private void checkAccess(boolean isNotEqualsId, Authentication authentication) {

        Boolean noAdminRoots = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) == false;

        if (isNotEqualsId && noAdminRoots) {
            throw new AccessDeniedException();
        }
    }
}
