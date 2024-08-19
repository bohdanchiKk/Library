package org.example.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.library.congif.UserDetails;
import org.example.library.entity.Book;
import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;
import org.example.library.entity.dto.mapper.UserMapper;
import org.example.library.repository.BookRepository;
import org.example.library.repository.UserRepository;
import org.example.library.security.UserMailService;
import org.example.library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder encoder;
    private final UserMailService mailService;


    @Override
    public UserDTO createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setVerificationCode(RandomStringUtils.randomAlphanumeric(32));
        user.setEnabled(false);
        userRepository.save(user);
        mailService.sendVerificationMail(user);
       return UserMapper.toDTO(user);
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
    public User verify(String code) {
        Optional<User> verifyUser = userRepository.findByVerificationCode(code);
        if (verifyUser.isPresent()){
            verifyUser.get().setVerificationCode(null);
            verifyUser.get().setEnabled(true);
            return userRepository.save(verifyUser.get());
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id or book id does not exist.");
    }

    @Scheduled(fixedRate = 60000)
    @Override
    public String cleanUp() {
        LocalDateTime cutOffTime = LocalDateTime.now().minusMinutes(1);
        Optional<List<User>> unverifiedUsers = userRepository.findAllByEnabledFalseAndCreationTimeBefore(cutOffTime);

        if (unverifiedUsers.isPresent()){
            userRepository.deleteAll(unverifiedUsers.get());
        }
        return "cleaned up";
    }

    @Override
    public String delete() {
        userRepository.deleteAll();
        return "deleted!";
    }
}
