package com.example.bookshop.service;

import com.example.bookshop.Exception.BookNotFoundException;
import com.example.bookshop.Repository.BookRepository;
import com.example.bookshop.Repository.OrderRepository;
import com.example.bookshop.feign.OrderClient;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
    public class BookService {

        private BookRepository bookRepository;
        private BookMapper bookMapper;
        private OrderClient orderClient;

@Autowired
        public BookService(BookRepository bookRepository, BookMapper bookMapper, OrderClient orderClient){
            this.bookRepository = bookRepository;
            this.bookMapper = bookMapper;
            this.orderClient = orderClient;
        }

        public List<BookDetails> getAllBooks() {
            return bookRepository.findAll().stream()
                    .map(bookMapper::toDetails)
                    .collect(Collectors.toList());
        }

        public BookDetails getBookById(Long id){
            return bookRepository.findById(id)
                    .map(book -> {
                        book.setViews(book.getViews() + 1);
                        bookRepository.save(book);
                        return bookMapper.toDetails(book);
                    })
                    .orElseThrow(() -> new BookNotFoundException("Nie znaleziono ksiazki z id " +id));

        }

        public List<BookDetails> filteredBooks(String surname) {
            return bookRepository.findByAuthorSurname(surname).stream()
                    .map(bookMapper::toDetails)
                    .collect(Collectors.toList());
        }

        @NotNull
        public BookDetails addBook(BookCreateRequest request){
            Book book = bookMapper.toEntity(request);
            return bookMapper.toDetails(bookRepository.save(book));
        }
        public BookDetails updateBook(Long id, BookCreateRequest request){
           return bookRepository.findById(id)
                   .map(existingBook -> {
                       bookMapper.updateEntity(request, existingBook);
                       return bookMapper.toDetails(bookRepository.save(existingBook));
                   })
                   .orElseThrow(() -> new BookNotFoundException("Nie znaleziono ksiazki z id " +id));
        }

        public void deleteBook(Long id){
            if (!bookRepository.existsById(id)) {
                throw new BookNotFoundException("Nie znalkeziono ksiazki z id " +id);
            }
            bookRepository.deleteById(id);
        }

        public List<BookToOrderDetails> getBooksToOrder() {
            return bookRepository.findBooksToOrder().stream()
                    .map(bookMapper::toOrderDetails)
                    .collect(Collectors.toList());
        }

        public BookToOrderDetails orderBook (BookCreateRequest request){
            Book book = bookMapper.toEntity(request);
            return bookMapper.toOrderDetails(book);
        }

        public List<Order> getOrderReport() {
            return orderClient.getAllOrders();
        }


    }