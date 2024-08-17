package org.example.library.service.impl;

import org.example.library.entity.Book;
import org.example.library.entity.dto.BookDTO;
import org.example.library.entity.dto.mapper.BookMapper;
import org.example.library.repository.BookRepository;
import org.example.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(BookDTO book) {
        return bookRepository.save(BookMapper.toEntity(book));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public String delete() {
        bookRepository.deleteAll();
        return "deleted!";
    }
}
