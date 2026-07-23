package com.abc.multiVendorEProject.service.Customer;

import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.OrderItem;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepository;

    public byte[] generateInvoice(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Order not found"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A4,40,40,40,40);

            PdfWriter.getInstance(document,out);

            document.open();

            addHeader(document);

            addOrderInformation(document,order);

            addShippingAddress(document,order);

            addPaymentInformation(document,order);

            addItemsTable(document,order);

            addSummary(document,order);

            addFooter(document);

            document.close();

            return out.toByteArray();

        } catch (Exception e){

            throw new RuntimeException(e);

        }

    }



//    ===========================font helper========================

    private final Font titleFont =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    22);

    private final Font headingFont =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    13);

    private final Font normalFont =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    11);

    private final Font smallFont =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    9);

    private final Font boldFont =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    11);


//    =======================Header Method=======================

    private void addHeader(Document document)
            throws DocumentException {

        Paragraph company = new Paragraph(
                "PURION MULTI VENDOR SHOP",
                titleFont);

        company.setAlignment(Element.ALIGN_CENTER);

        document.add(company);

        Paragraph website = new Paragraph(
                "www.purionShop.com",
                smallFont);

        website.setAlignment(Element.ALIGN_CENTER);

        document.add(website);

        document.add(Chunk.NEWLINE);

        Paragraph invoice = new Paragraph(
                "INVOICE",
                headingFont);

        invoice.setAlignment(Element.ALIGN_CENTER);

        document.add(invoice);

        document.add(Chunk.NEWLINE);

    }


//    ========================Order Information Section==========================

    private void addOrderInformation(Document document, Order order)
            throws DocumentException {

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(100);

        table.setSpacingBefore(10);

        table.setSpacingAfter(15);

        table.setWidths(new float[]{1, 1});

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

        table.addCell(createInfoCell(
                "Invoice No",
                "INV-" + order.getOrderNumber()));

        table.addCell(createInfoCell(
                "Order No",
                order.getOrderNumber()));

        table.addCell(createInfoCell(
                "Order Date",
                order.getCreatedAt().format(formatter)));

        table.addCell(createInfoCell(
                "Customer",
                order.getUser().getUserName()));

        document.add(table);
    }

    private PdfPCell createInfoCell(String label, String value) {

        Paragraph paragraph = new Paragraph();

        paragraph.add(new Chunk(label + " : ", boldFont));

        paragraph.add(new Chunk(value, normalFont));

        PdfPCell cell = new PdfPCell(paragraph);

        cell.setPadding(8);

        cell.setBorder(Rectangle.BOX);

        return cell;
    }

    private void addItemsTable(Document document, Order order)
            throws DocumentException {

        Paragraph title = new Paragraph(
                "ORDER ITEMS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)
        );

        title.setSpacingBefore(20);
        title.setSpacingAfter(10);

        document.add(title);

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100);

        table.setWidths(new float[]{1f, 4f, 2f, 1f, 2f, 2f});

        table.setSpacingBefore(5);

        table.addCell(createHeaderCell("SL"));
        table.addCell(createHeaderCell("Product"));
        table.addCell(createHeaderCell("Variant"));
        table.addCell(createHeaderCell("Qty"));
        table.addCell(createHeaderCell("Unit Price"));
        table.addCell(createHeaderCell("Total"));

        int sl = 1;

        for (OrderItem item : order.getOrderItems()) {

            table.addCell(createBodyCell(String.valueOf(sl++)));

            table.addCell(createBodyCell(item.getVariant().getProduct().getName()));

            table.addCell(createBodyCell(String.valueOf(item.getQuantity())));

            table.addCell(createBodyCell(formatMoney(item.getUnitPrice())));

            table.addCell(createBodyCell(formatMoney(item.getTotalPrice())));
        }

        document.add(table);
    }

    private PdfPCell createHeaderCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                11,
                Color.WHITE
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setBackgroundColor(new Color(52,73,94));

        cell.setPadding(8);

        return cell;
    }

    private PdfPCell createBodyCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA,
                10
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setPadding(7);

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        return cell;
    }

    private String formatMoney(BigDecimal amount){

        return String.format("৳ %,.2f", amount);
    }


    private void addSummary(Document document, Order order)
            throws DocumentException {

        Paragraph title = new Paragraph(
                "PAYMENT SUMMARY",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)
        );

        title.setSpacingBefore(20);
        title.setSpacingAfter(10);

        document.add(title);

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidths(new float[]{2f, 1.5f});

        table.addCell(createSummaryLabelCell("Subtotal"));
        table.addCell(createSummaryValueCell(formatMoney(order.getSubtotal())));

        table.addCell(createSummaryLabelCell("Shipping"));
        table.addCell(createSummaryValueCell(formatMoney(order.getShippingFee())));

        table.addCell(createSummaryLabelCell("Discount"));
        table.addCell(createSummaryValueCell("- " + formatMoney(order.getDiscount())));

        table.addCell(createGrandTotalLabelCell("Grand Total"));
        table.addCell(createGrandTotalValueCell(formatMoney(order.getTotalPrice())));

        document.add(table);
    }

    private PdfPCell createSummaryLabelCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA,
                11
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(6);

        return cell;
    }


    private PdfPCell createSummaryValueCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA,
                11
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(6);

        return cell;
    }

    private PdfPCell createGrandTotalLabelCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                12,
                Color.WHITE
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBackgroundColor(new Color(52,73,94));
        cell.setPadding(8);

        return cell;
    }


    private PdfPCell createGrandTotalValueCell(String text){

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                12,
                Color.WHITE
        );

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBackgroundColor(new Color(52,73,94));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(8);

        return cell;
    }


    private void addFooter(Document document) throws DocumentException {

        document.add(Chunk.NEWLINE);

        LineSeparator line = new LineSeparator();
        line.setLineColor(new Color(180, 180, 180));

        document.add(line);

        Paragraph thanks = new Paragraph(
                "Thank you for shopping with us!",
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        14,
                        new Color(46, 125, 50)
                )
        );

        thanks.setAlignment(Element.ALIGN_CENTER);
        thanks.setSpacingBefore(12);

        document.add(thanks);

        Paragraph info = new Paragraph();

        info.setAlignment(Element.ALIGN_CENTER);

        info.setSpacingBefore(8);

        info.add(new Phrase(
                "PURION Multi Vendor E-Commerce\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)
        ));

        info.add(new Phrase(
                "Dhaka, Bangladesh\n",
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        ));

        info.add(new Phrase(
                "Phone: +8801700000000\n",
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        ));

        info.add(new Phrase(
                "Email: purion@gmail.com\n",
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        ));

        info.add(new Phrase(
                "Website: www.purion.com\n",
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        ));

        document.add(info);

        Paragraph note = new Paragraph(
                "This is a computer generated invoice. No signature is required.",
                FontFactory.getFont(
                        FontFactory.HELVETICA_OBLIQUE,
                        9,
                        Color.GRAY
                )
        );

        note.setAlignment(Element.ALIGN_CENTER);
        note.setSpacingBefore(15);

        document.add(note);
    }


//    ===========================Shipping Address Section========================

    private void addShippingAddress(Document document, Order order)
            throws DocumentException {

        Paragraph title = new Paragraph(
                "SHIPPING ADDRESS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)
        );

        title.setSpacingBefore(20);
        title.setSpacingAfter(10);

        document.add(title);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(10);

        cell.addElement(new Paragraph(
                order.getShippingAddress().getUser().getUserName(),
                boldFont));

        cell.addElement(new Paragraph(
                "Phone : " + order.getShippingAddress().getPhone(),
                normalFont));

        cell.addElement(new Paragraph(
                order.getShippingAddress().getStreet(),
                normalFont));

        cell.addElement(new Paragraph(
                order.getShippingAddress().getCity()
                        + ", "
                        + order.getShippingAddress().getDistrict(),
                normalFont));

        cell.addElement(new Paragraph(
                order.getShippingAddress().getCountry()
                        + " - "
                        + order.getShippingAddress().getZipCode(),
                normalFont));

        table.addCell(cell);

        document.add(table);
    }


//    ===========================Payment Information Section=========================


    private void addPaymentInformation(Document document, Order order)
            throws DocumentException {

        Paragraph title = new Paragraph(
                "PAYMENT INFORMATION",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)
        );

        title.setSpacingBefore(20);
        title.setSpacingAfter(10);

        document.add(title);

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 1f});

        table.addCell(createInfoCell(
                "Payment Method",
                order.getPayment().getPaymentMethod().name()
        ));

        table.addCell(createInfoCell(
                "Payment Status",
                order.getPayment().getPaymentStatus().name()
        ));

        document.add(table);
    }


}