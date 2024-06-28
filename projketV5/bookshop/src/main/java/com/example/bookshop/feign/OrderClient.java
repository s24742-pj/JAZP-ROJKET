package com.example.bookshop.feign;
import org.springframework.cloud.openfeign.FeignClient;
import com.example.bookshop.model.Order;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "order-service", url = "http://localhost:8080")
public interface OrderClient {

    @GetMapping("/order")
    List<Order> getAllOrders();


}
