package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    boolean existsById(Long id);
    UserProfile getById(Long id);
}
