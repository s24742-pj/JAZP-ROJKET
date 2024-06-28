package com.example.bookshop.controler;


import com.example.bookshop.model.*;
import com.example.bookshop.sercurity.AdminPermission;
import com.example.bookshop.sercurity.UserPermission;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookshop")
public class BookServiceControler {

    private final BookService bookService;

    @Autowired
    public BookServiceControler(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public List<BookDetails> getAllBooks() {
        return bookService.getAllBooks();
    }

    public BookDetails getBook(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @UserPermission
    @GetMapping("/author/{surname}")
    public List<BookDetails> getFilteredBooks(@PathVariable String surname){
        return bookService.filteredBooks(surname);
    }

    @AdminPermission
    @PostMapping("/add")
    public BookDetails addBook (@RequestBody BookCreateRequest bookCreateRequest){
        return bookService.addBook(bookCreateRequest);
    }

    @AdminPermission
    @PutMapping("/{id}")
    public BookDetails updateBook(@PathVariable Long id, @RequestBody BookCreateRequest bookCreateRequest){
        return bookService.updateBook(id, bookCreateRequest);
    }

    @AdminPermission
    @DeleteMapping("/del/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @UserPermission
    @PostMapping("/order")
    public BookToOrderDetails addOrder(@RequestBody BookCreateRequest bookOrder) {

        return bookService.orderBook(bookOrder);
    }

    @GetMapping(value = "/books_to_order", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookToOrderDetails> getBooksToOrder() {
        return bookService.getBooksToOrder();
    }

    @AdminPermission
    @GetMapping("/order-report")
    public List<Order> getOrderReport() {
        return bookService.getOrderReport();
    }
}
