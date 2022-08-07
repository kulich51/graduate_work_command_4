package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserProfile;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfile UserToUserProfile(User user);
    User userProfileToUser(UserProfile userProfile);
}
