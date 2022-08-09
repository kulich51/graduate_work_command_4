package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;
import java.util.Optional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> getByAdsId(Long adsId);
    void deleteByAdsIdAndId(Long adsId, Long commentId);
    Optional<Comment> getByAdsIdAndId(Long adsId, Long commentId);
    void deleteAllByAdsId(Long adsId);
}
