package com.example.bookshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Author {

    @Id
    private Long id;
    private String name;
    private String surname;

}
