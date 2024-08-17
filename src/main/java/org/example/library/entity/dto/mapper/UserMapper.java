package org.example.library.entity.dto.mapper;

import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .books(user.getBooks() != null ?
                        user.getBooks().stream().map(BookMapper::toDTO)
                        .collect(Collectors.toUnmodifiableList()) : new ArrayList<>())
                .build();
    }

    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .books(userDTO.getBooks() != null ?
                        userDTO.getBooks().stream().map(BookMapper::toEntity)
                        .collect(Collectors.toUnmodifiableList()) : new ArrayList<>())
                .build();
    }
}
