package com.example.bookshop.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookCreateRequest {

    @Id
    private Long id;
    private String title;
    private String genre;
    private double price;
    private int pages;
    private int views;
    private int availableQuantity;
    private Long author_id;
}
