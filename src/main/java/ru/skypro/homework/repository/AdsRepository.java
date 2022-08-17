package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

@Transactional
public interface AdsRepository extends JpaRepository<Ads, Long> {

    void deleteAllById(Long adsId);
    Collection<Ads> findByTitleContainsOrderByTitle(String title);
}
