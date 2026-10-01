package com.sadguru.orderinvoice.service;

import com.sadguru.orderinvoice.entity.Order;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfInvoiceService {

    private static final Logger log =
            LoggerFactory.getLogger(PdfInvoiceService.class);

    public byte[] generateInvoice(Order order) {

        log.info("Generating invoice PDF for order ID: {}", order.getId());

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document =
                new Document(PageSize.A4, 40, 40, 40, 40);

        try {

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font companyFont = new Font(
                    Font.HELVETICA,
                    20,
                    Font.BOLD
            );

            Font invoiceFont = new Font(
                    Font.HELVETICA,
                    24,
                    Font.BOLD
            );

            Font normalFont = new Font(
                    Font.HELVETICA,
                    10,
                    Font.NORMAL
            );

            Font boldFont = new Font(
                    Font.HELVETICA,
                    10,
                    Font.BOLD
            );

            Font smallFont = new Font(
                    Font.HELVETICA,
                    9,
                    Font.NORMAL
            );

            PdfPTable headerTable =
                    new PdfPTable(2);

            headerTable.setWidthPercentage(100);

            headerTable.setWidths(
                    new float[]{60, 40}
            );

            PdfPCell companyCell =
                    new PdfPCell();

            companyCell.setBorder(
                    Rectangle.NO_BORDER
            );

            Paragraph companyName =
                    new Paragraph(
                            "ORDER INVOICE SYSTEM",
                            companyFont
                    );

            companyCell.addElement(companyName);

            Paragraph companyDetails =
                    new Paragraph(
                            "Professional Order Management\n" +
                                    "Email: support@example.com\n" +
                                    "Phone: +91 XXXXX XXXXX",
                            smallFont
                    );

            companyCell.addElement(companyDetails);

            headerTable.addCell(companyCell);

            PdfPCell invoiceCell =
                    new PdfPCell();

            invoiceCell.setBorder(
                    Rectangle.NO_BORDER
            );

            invoiceCell.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            Paragraph invoiceTitle =
                    new Paragraph(
                            "INVOICE",
                            invoiceFont
                    );

            invoiceTitle.setAlignment(
                    Element.ALIGN_RIGHT
            );

            invoiceCell.addElement(invoiceTitle);

            Paragraph invoiceNumber =
                    new Paragraph(
                            "Invoice #: INV-" + order.getId(),
                            normalFont
                    );

            invoiceNumber.setAlignment(
                    Element.ALIGN_RIGHT
            );

            invoiceCell.addElement(invoiceNumber);

            String invoiceDate =
                    LocalDate.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "dd MMM yyyy"
                                    )
                            );

            Paragraph date =
                    new Paragraph(
                            "Date: " + invoiceDate,
                            normalFont
                    );

            date.setAlignment(
                    Element.ALIGN_RIGHT
            );

            invoiceCell.addElement(date);

            headerTable.addCell(invoiceCell);

            document.add(headerTable);

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable billingTable =
                    new PdfPTable(2);

            billingTable.setWidthPercentage(100);

            billingTable.setWidths(
                    new float[]{50, 50}
            );

            PdfPCell billToCell =
                    new PdfPCell();

            billToCell.setPadding(10);

            Paragraph billToTitle =
                    new Paragraph(
                            "BILL TO",
                            boldFont
                    );

            billToCell.addElement(
                    billToTitle
            );

            billToCell.addElement(
                    new Paragraph(
                            order.getCustomerName(),
                            normalFont
                    )
            );

            billToCell.addElement(
                    new Paragraph(
                            order.getCustomerEmail(),
                            normalFont
                    )
            );

            billingTable.addCell(
                    billToCell
            );

            PdfPCell orderInfoCell =
                    new PdfPCell();

            orderInfoCell.setPadding(10);

            Paragraph orderInfoTitle =
                    new Paragraph(
                            "ORDER INFORMATION",
                            boldFont
                    );

            orderInfoCell.addElement(
                    orderInfoTitle
            );

            orderInfoCell.addElement(
                    new Paragraph(
                            "Order ID: #" + order.getId(),
                            normalFont
                    )
            );

            orderInfoCell.addElement(
                    new Paragraph(
                            "Order Date: " + invoiceDate,
                            normalFont
                    )
            );

            billingTable.addCell(
                    orderInfoCell
            );

            document.add(
                    billingTable
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable productTable =
                    new PdfPTable(4);

            productTable.setWidthPercentage(100);

            productTable.setWidths(
                    new float[]{45, 15, 20, 20}
            );

            PdfPCell productHeader =
                    new PdfPCell(
                            new Phrase(
                                    "PRODUCT",
                                    boldFont
                            )
                    );

            productHeader.setPadding(8);
            productHeader.setHorizontalAlignment(
                    Element.ALIGN_LEFT
            );

            productTable.addCell(
                    productHeader
            );

            PdfPCell quantityHeader =
                    new PdfPCell(
                            new Phrase(
                                    "QTY",
                                    boldFont
                            )
                    );

            quantityHeader.setPadding(8);
            quantityHeader.setHorizontalAlignment(
                    Element.ALIGN_CENTER
            );

            productTable.addCell(
                    quantityHeader
            );

            PdfPCell priceHeader =
                    new PdfPCell(
                            new Phrase(
                                    "PRICE",
                                    boldFont
                            )
                    );

            priceHeader.setPadding(8);
            priceHeader.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            productTable.addCell(
                    priceHeader
            );

            PdfPCell totalHeader =
                    new PdfPCell(
                            new Phrase(
                                    "TOTAL",
                                    boldFont
                            )
                    );

            totalHeader.setPadding(8);
            totalHeader.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            productTable.addCell(
                    totalHeader
            );

            double total =
                    order.getPrice()
                            * order.getQuantity();

            PdfPCell productCell =
                    new PdfPCell(
                            new Phrase(
                                    order.getProductName(),
                                    normalFont
                            )
                    );

            productCell.setPadding(8);

            productTable.addCell(
                    productCell
            );

            PdfPCell quantityCell =
                    new PdfPCell(
                            new Phrase(
                                    String.valueOf(
                                            order.getQuantity()
                                    ),
                                    normalFont
                            )
                    );

            quantityCell.setPadding(8);

            quantityCell.setHorizontalAlignment(
                    Element.ALIGN_CENTER
            );

            productTable.addCell(
                    quantityCell
            );

            PdfPCell priceCell =
                    new PdfPCell(
                            new Phrase(
                                    "₹" +
                                            String.format(
                                                    "%.2f",
                                                    order.getPrice()
                                            ),
                                    normalFont
                            )
                    );

            priceCell.setPadding(8);

            priceCell.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            productTable.addCell(
                    priceCell
            );

            PdfPCell totalCell =
                    new PdfPCell(
                            new Phrase(
                                    "₹" +
                                            String.format(
                                                    "%.2f",
                                                    total
                                            ),
                                    boldFont
                            )
                    );

            totalCell.setPadding(8);

            totalCell.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            productTable.addCell(
                    totalCell
            );

            document.add(
                    productTable
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable totalTable =
                    new PdfPTable(2);

            totalTable.setWidthPercentage(45);

            totalTable.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            totalTable.setWidths(
                    new float[]{50, 50}
            );

            PdfPCell totalLabel =
                    new PdfPCell(
                            new Phrase(
                                    "TOTAL AMOUNT",
                                    boldFont
                            )
                    );

            totalLabel.setBorder(
                    Rectangle.NO_BORDER
            );

            totalLabel.setPadding(8);

            totalTable.addCell(
                    totalLabel
            );

            PdfPCell totalValue =
                    new PdfPCell(
                            new Phrase(
                                    "₹" +
                                            String.format(
                                                    "%.2f",
                                                    total
                                            ),
                                    boldFont
                            )
                    );

            totalValue.setBorder(
                    Rectangle.NO_BORDER
            );

            totalValue.setPadding(8);

            totalValue.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            totalTable.addCell(
                    totalValue
            );

            document.add(
                    totalTable
            );

            document.add(
                    new Paragraph(" ")
            );

            Paragraph thankYou =
                    new Paragraph(
                            "Thank you for your business!",
                            boldFont
                    );

            thankYou.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(
                    thankYou
            );

            Paragraph footer =
                    new Paragraph(
                            "This is a computer-generated invoice.",
                            smallFont
                    );

            footer.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(
                    footer
            );

        } catch (Exception e) {

            log.error("Failed to generate invoice PDF for order ID: {}",
                    order.getId(), e);

            throw new RuntimeException(
                    "Failed to generate invoice PDF", e
            );

        } finally {

            document.close();
        }

        log.info("Invoice PDF generated successfully for order ID: {}",
                order.getId());

        return outputStream.toByteArray();
    }
}