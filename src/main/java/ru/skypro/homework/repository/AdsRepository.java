package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ads;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AdsRepository extends JpaRepository<Ads, Long> {

    void deleteAllById(Long adsId);
//    @Query(nativeQuery = true,
//           value = "select a, up " +
//                   "from ads a, users_profiles up " +
//                   "where a.id = :adsId and up.id = :userProfileId")
//    List<Object[]> getFullAds(Long adsId, Long userProfileId);
//
//    @Query (nativeQuery = true,
//            value = "select user_profile_id from ads where id = :adsId")
//    Long getUserProfileId(Long adsId);

    Optional<Ads> findById(Long adsId);
}
