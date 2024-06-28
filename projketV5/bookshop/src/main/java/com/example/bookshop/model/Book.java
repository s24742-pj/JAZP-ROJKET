package com.example.bookshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String genre;
    private double price;
    private int pages;
    private int views;
    private int availableQuantity;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
