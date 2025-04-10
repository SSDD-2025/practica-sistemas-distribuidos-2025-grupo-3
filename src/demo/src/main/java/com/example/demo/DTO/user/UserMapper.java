package com.example.demo.DTO.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "isFollowing", expression = "java(checkIsFollowing(currentUser, user))")
    FollowingUserDTO toDTO(User user, @Context User currentUser);

    @Mapping(target = "date", source = "dateJoined", dateFormat = "dd/MM/yyyy HH:mm")
    UserDTOBasic toDTO(User user);

    @Mapping(target = "date", source = "dateJoined", dateFormat = "dd/MM/yyyy HH:mm")
    List<UserDTOBasic> toDTOsBasic(Collection<User> users);

    User toDomain(FollowingUserDTO userDTO);

    List<FollowingUserDTO> toDTOs(Collection<User> users, @Context User currentUser);

    default boolean checkIsFollowing(User currentUser, User user) {
        return currentUser.getFriends() != null && currentUser.getFriends().contains(user);
    }
}
