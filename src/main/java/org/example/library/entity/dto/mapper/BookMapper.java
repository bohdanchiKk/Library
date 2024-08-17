package org.example.library.entity.dto.mapper;

import org.example.library.entity.Book;
import org.example.library.entity.dto.BookDTO;

public class BookMapper {

    public static BookDTO toDTO(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .build();
    }

    public static Book toEntity(BookDTO bookDTO){
        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .description(bookDTO.getDescription())
                .build();
    }

}
