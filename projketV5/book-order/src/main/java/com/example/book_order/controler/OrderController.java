package com.example.book_order.controler;

import com.example.book_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService= orderService;
    }

    public ResponseEntity<byte[]> printOrders() {
        byte[] pdfContent = orderService.generatePdfReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orders.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}




