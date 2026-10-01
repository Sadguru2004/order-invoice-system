package com.sadguru.orderinvoice.controller;

import com.sadguru.orderinvoice.dto.OrderRequest;
import com.sadguru.orderinvoice.dto.OrderResponse;
import com.sadguru.orderinvoice.service.OrderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request) throws MessagingException {

        return orderService.createOrder(request);
    }
}