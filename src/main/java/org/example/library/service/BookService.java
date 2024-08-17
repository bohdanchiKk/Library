package org.example.library.service;

import org.example.library.entity.Book;
import org.example.library.entity.dto.BookDTO;

import java.util.List;

public interface BookService {
    Book createBook(BookDTO book);
    List<Book> getAll();
    String delete();
}
