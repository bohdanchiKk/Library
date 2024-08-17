package org.example.library.service;

import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;

import java.util.List;

public interface UserService {
    User createUser(UserDTO user);
    List<User> getAll();
    UserDTO setBook(Long userId,Long bookId);
    UserDTO unsetBook(Long userId, Long bookId);
    String delete();
}
