package org.example.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.library.entity.Book;
import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;
import org.example.library.entity.dto.mapper.UserMapper;
import org.example.library.repository.BookRepository;
import org.example.library.repository.UserRepository;
import org.example.library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @Override
    public User createUser(UserDTO user) {
       return userRepository.save(UserMapper.toEntity(user));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO setBook(Long userId, Long bookId) {
        Optional<User> user = userRepository.findById(userId);
        var book = bookRepository.findById(bookId);
        if (user.isPresent() && book.isPresent()){
            var presentUser = user.get();
            var presentBook = book.get();
            if (presentBook.getUser() != null){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"User is already borrowed.");
            }
            presentUser.getBooks().add(presentBook);
            presentBook.setUser(presentUser);
            userRepository.save(presentUser);
            return (UserMapper.toDTO(presentUser));
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id or book id does not exist.");


    }

    @Override
    public UserDTO unsetBook(Long userId, Long bookId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isPresent() && book.isPresent()){

            var presentUser = user.get();
            var presentBook = book.get();
            presentUser.getBooks().remove(presentBook);
            presentBook.setUser(null);
            userRepository.save(presentUser);
            return (UserMapper.toDTO(presentUser));

        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id or book id does not exist.");
    }

    @Override
    public String delete() {
        userRepository.deleteAll();
        return "deleted!";
    }
}
