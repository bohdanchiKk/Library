package org.example.library.controller;

import lombok.RequiredArgsConstructor;
import org.example.library.entity.Book;
import org.example.library.entity.dto.BookDTO;
import org.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody BookDTO book){
        return bookService.createBook(book);
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getAll();
    }

    @DeleteMapping("/books")
    public String delete(){
        return bookService.delete();
    }
}
