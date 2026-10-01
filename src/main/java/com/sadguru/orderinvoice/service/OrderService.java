package com.sadguru.orderinvoice.service;

import com.sadguru.orderinvoice.dto.OrderRequest;
import com.sadguru.orderinvoice.dto.OrderResponse;
import com.sadguru.orderinvoice.entity.Order;
import com.sadguru.orderinvoice.repository.OrderRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final EmailService emailService;

    private final PdfInvoiceService pdfInvoiceService;

    public OrderService(OrderRepository orderRepository,
                        EmailService emailService,
                        PdfInvoiceService pdfInvoiceService) {

        this.orderRepository = orderRepository;
        this.emailService = emailService;
        this.pdfInvoiceService = pdfInvoiceService;
    }

    public OrderResponse createOrder(OrderRequest request) throws MessagingException {

        try {

            log.info("Creating order for customer: {}",
                    request.getCustomerEmail());

            Order order = new Order();

            order.setCustomerName(request.getCustomerName());
            order.setCustomerEmail(request.getCustomerEmail());
            order.setProductName(request.getProductName());
            order.setQuantity(request.getQuantity());
            order.setPrice(request.getPrice());

            Order savedOrder = orderRepository.save(order);

            log.info("Order saved successfully with ID: {}",
                    savedOrder.getId());

            byte[] pdfInvoice =
                    pdfInvoiceService.generateInvoice(savedOrder);

            emailService.sendOrderConfirmationEmail(
                    savedOrder.getCustomerEmail(),
                    savedOrder.getCustomerName(),
                    savedOrder.getId(),
                    savedOrder.getProductName(),
                    savedOrder.getQuantity(),
                    savedOrder.getPrice(),
                    pdfInvoice
            );

            log.info("Invoice email sent successfully to: {}",
                    savedOrder.getCustomerEmail());

            return new OrderResponse(
                    true,
                    "Order created successfully",
                    savedOrder.getId()
            );

        } catch (Exception e) {

            log.error(
                    "Failed to process order for customer: {}",
                    request.getCustomerEmail(),
                    e
            );

            throw e;
        }
    }
}