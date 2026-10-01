package com.sadguru.orderinvoice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderConfirmationEmail(
            String customerEmail,
            String customerName,
            Long orderId,
            String productName,
            Integer quantity,
            Double price,
            byte[] pdfInvoice) throws MessagingException {

        log.info("Preparing invoice email for: {}", customerEmail);

        double total = price * quantity;

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(customerEmail);

        helper.setSubject(
                "Order Confirmation - Order #" + orderId
        );

        String htmlContent =
                "<html>" +
                        "<body style='margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, sans-serif;'>" +

                        "<div style='max-width:600px; margin:30px auto; background:white; border-radius:10px; overflow:hidden;'>" +

                        "<div style='background-color:#2563eb; padding:25px; text-align:center;'>" +
                        "<h1 style='color:white; margin:0;'>Order Confirmation</h1>" +
                        "<p style='color:white; margin:8px 0 0;'>Thank you for your order!</p>" +
                        "</div>" +

                        "<div style='padding:30px;'>" +

                        "<p style='font-size:16px;'>Hello <b>" +
                        customerName +
                        "</b>,</p>" +

                        "<p style='font-size:15px; color:#555;'>" +
                        "Your order has been successfully placed." +
                        "</p>" +

                        "<p><b>Order ID:</b> #" +
                        orderId +
                        "</p>" +

                        "<table style='width:100%; border-collapse:collapse; margin-top:20px;'>" +

                        "<tr style='background-color:#f1f5f9;'>" +

                        "<th style='padding:12px; border:1px solid #ddd; text-align:left;'>" +
                        "Product" +
                        "</th>" +

                        "<th style='padding:12px; border:1px solid #ddd;'>" +
                        "Quantity" +
                        "</th>" +

                        "<th style='padding:12px; border:1px solid #ddd;'>" +
                        "Price" +
                        "</th>" +

                        "<th style='padding:12px; border:1px solid #ddd;'>" +
                        "Total" +
                        "</th>" +

                        "</tr>" +

                        "<tr>" +

                        "<td style='padding:12px; border:1px solid #ddd;'>" +
                        productName +
                        "</td>" +

                        "<td style='padding:12px; border:1px solid #ddd; text-align:center;'>" +
                        quantity +
                        "</td>" +

                        "<td style='padding:12px; border:1px solid #ddd; text-align:right;'>" +
                        "₹" + String.format("%.2f", price) +
                        "</td>" +

                        "<td style='padding:12px; border:1px solid #ddd; text-align:right; font-weight:bold;'>" +
                        "₹" + String.format("%.2f", total) +
                        "</td>" +

                        "</tr>" +

                        "</table>" +

                        "<div style='margin-top:25px; padding:15px; background-color:#f8fafc; text-align:right;'>" +

                        "<span style='font-size:18px; font-weight:bold;'>" +
                        "Total Amount: " +
                        "</span>" +

                        "<span style='font-size:20px; font-weight:bold; color:#2563eb;'>" +
                        "₹" + String.format("%.2f", total) +
                        "</span>" +

                        "</div>" +

                        "<p style='margin-top:30px; color:#555;'>" +
                        "Your invoice is attached to this email as a PDF." +
                        "</p>" +

                        "<p style='margin-top:25px;'>" +
                        "Regards,<br>" +
                        "<b>Order Invoice System</b>" +
                        "</p>" +

                        "</div>" +

                        "<div style='background-color:#f1f5f9; padding:15px; text-align:center;'>" +
                        "<p style='margin:0; font-size:12px; color:#777;'>" +
                        "This is an automated email. Please do not reply." +
                        "</p>" +
                        "</div>" +

                        "</div>" +

                        "</body>" +
                        "</html>";


        helper.setText(htmlContent, true);

        helper.addAttachment(
                "Invoice-" + orderId + ".pdf",
                new ByteArrayResource(pdfInvoice)
        );

        try {

            log.info("Sending invoice email for order ID: {}", orderId);

            mailSender.send(message);

            log.info("Invoice email sent successfully to: {}", customerEmail);

        } catch (Exception e) {

            log.error(
                    "Failed to send invoice email to: {} for order ID: {}",
                    customerEmail,
                    orderId,
                    e
            );

            throw e;
        }
    }
}