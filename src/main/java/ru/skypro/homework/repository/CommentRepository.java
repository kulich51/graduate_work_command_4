package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> getByAdsId(Long adsId);

    @Transactional
    void deleteByAdsIdAndId(Long adsId, Long commentId);
}
