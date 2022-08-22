package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

@Transactional
public interface AdsRepository extends JpaRepository<Ads, Long> {

    void deleteAllById(Long adsId);
    Collection<Ads> findByTitleContainsOrderByTitle(String title);

    @Query(nativeQuery = true, value = "select user_profile_id from ads where id = ?1")
    Long getUserProfileId(Long adsId);
}
