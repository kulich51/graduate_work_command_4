package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByEmail(String email);

    @Query(nativeQuery = true, value = "select id from users_profiles where email like ?1")
    Long getUserProfileId(String email);
}
