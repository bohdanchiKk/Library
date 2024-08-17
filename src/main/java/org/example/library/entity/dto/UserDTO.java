package org.example.library.entity.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder(access = AccessLevel.PUBLIC)
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<BookDTO> books;
}
