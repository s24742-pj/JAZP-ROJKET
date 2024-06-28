package com.example.book_order.service;

import com.example.book_order.mapper.OrderMapper;
import com.example.book_order.model.Order;
import com.example.book_order.repository.OrderRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<Order> createOrders(List<Order> bookVisits) {
        List<Order> processedOrders = bookVisits.stream()
                .map(bookVisit -> {
                    Optional<Order> existingOrderOpt = orderRepository.findByBookId(bookVisit.getBookId());
                    if (existingOrderOpt.isPresent()) {
                        Order existingOrder = existingOrderOpt.get();
                        if (bookVisit.getQuantity() % 10 == 0) {
                            int countToOrder = bookVisit.getQuantity() / 10;
                            existingOrder.setQuantity(existingOrder.getQuantity() + countToOrder);
                            return orderRepository.save(existingOrder);
                        }
                    } else {
                        if (bookVisit.getQuantity() % 10 == 0) {
                            int countToOrder = bookVisit.getQuantity() / 10;
                            Order newOrder = orderMapper.toEntity(bookVisit);
                            newOrder.setQuantity(countToOrder);
                            return orderRepository.save(newOrder);
                        }
                    }
                    return null;
                })
                .map(orderMapper::toDetails)
                .collect(Collectors.toList());

        return processedOrders;

    }

    public byte[] generatePdfReport() {
        List<Order> orders = orderRepository.findAll();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            document.add(new Paragraph("Order List: "));
            for (Order order : orders) {
                document.add(new Paragraph("Id ksiazki: " + order.getBookId()));
                document.add(new Paragraph("Ilosc: " + order.getQuantity()));
                document.add(new Paragraph("\n"));
            }
            } finally {
                document.close();
            }
            return byteArrayOutputStream.toByteArray();
        }

    }
