package org.example.library.controller;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;
import org.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAll();
    }
    // протестити чи видаляє силкі книжок на людей
    @DeleteMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String delete(){
        return userService.delete();
    }

    @PostMapping("/{userId}/{bookId}")
    public UserDTO setBook(@PathVariable Long userId, @PathVariable Long bookId){
        return userService.setBook(userId,bookId);
    }

    @DeleteMapping("/{userId}/{bookId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public UserDTO unSetBook(@PathVariable Long userId, @PathVariable Long bookId){
        return userService.unsetBook(userId,bookId);
    }

    @PostMapping("/verify")
    @Transactional
    public User verify(@RequestParam String code) {
        return userService.verify(code);
    }


}
