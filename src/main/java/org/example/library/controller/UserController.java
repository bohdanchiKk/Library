package org.example.library.controller;

import lombok.RequiredArgsConstructor;
import org.example.library.entity.User;
import org.example.library.entity.dto.UserDTO;
import org.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/users")
    public User createUser(@RequestBody UserDTO user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAll();
    }

    @DeleteMapping("/users")
    public String delete(){
        return userService.delete();
    }

    @PostMapping("/{userId}/{bookId}")
    public UserDTO setBook(@PathVariable Long userId, @PathVariable Long bookId){
        return userService.setBook(userId,bookId);
    }

    @DeleteMapping("/{userId}/{bookId}")
    public UserDTO unSetBook(@PathVariable Long userId, @PathVariable Long bookId){
        return userService.unsetBook(userId,bookId);
    }
}
