package org.example.library.service;

import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    UserDTO createUser(User user);
    List<User> getAll();
    UserDTO setBook(Long userId,Long bookId);
    UserDTO unsetBook(Long userId, Long bookId);
    User verify(String code);
    String cleanUp();
    String delete();
}
