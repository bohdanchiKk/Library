package org.example.library.entity.dto;

import lombok.*;

@Builder(access = AccessLevel.PUBLIC)
@Data
public class BookDTO {
    private Long id;
    private String name;
    private String description;
}
