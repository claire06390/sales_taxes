package com.example.sales_taxes.unit;

import com.example.sales_taxes.dto.PurchaseItem;
import com.example.sales_taxes.exception.BadRequestException;
import com.example.sales_taxes.service.PurchaseParserService;
import com.example.sales_taxes.service.ReceiptService;
import com.example.sales_taxes.utils.CategoryEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.example.sales_taxes.utils.Constant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReceiptServiceTest {

    @Mock
    private PurchaseParserService purchaseParserService;

    @InjectMocks
    private ReceiptService receiptService;

    public ReceiptServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReceipt_ValidPurchases_ReturnsCorrectReceipt() {
        // Given
        String purchases = "1 book at 12.49\n1 music CD at 14.99\n1 chocolate bar at 0.85";
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem("book", 1, 12.49f, false, CategoryEnum.BOOK));
        purchaseItems.add(new PurchaseItem("music CD", 1, 14.99f, false, CategoryEnum.OTHER));
        purchaseItems.add(new PurchaseItem("chocolate bar", 1, 0.85f, false, CategoryEnum.FOOD));
        when(purchaseParserService.parseAndCheckPurchases(purchases)).thenReturn(purchaseItems);

        // When
        String result = receiptService.createReceipt(purchases);

        // Then
        String expected = "1 book: 12.49\n1 music CD: 16.49\n1 chocolate bar: 0.85\nSales Taxes: 1.50 Total: 29.83";
        assertEquals(expected, result);
    }

    @Test
    void createReceipt_NoPurchases_ReturnsBadRequest() {
        // Given
        String purchases = "";

        // When/Then
        try {
            receiptService.createReceipt(purchases);
        } catch (BadRequestException e) {
            assertEquals("No purchase items found. Please check the format and try again.", e.getMessage());
        }
    }

    @Test
    void createReceipt_InvalidPrice_ReturnsBadRequest() {
        // Given
        String purchases = "1 book at -12.49";

        // When/Then
        try {
            receiptService.createReceipt(purchases);
        } catch (BadRequestException e) {
            assertEquals("The price of book cannot be negative or null", e.getMessage());
        }
    }

    @Test
    void createReceipt_ImportedPurchases_ReturnsCorrectReceipt() {
        // Given
        String purchases = "1 box of imported chocolates at 10.00\n1 imported bottle of perfume at 47.50";
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem("box of chocolates", 1, 10.00f, true, CategoryEnum.FOOD));
        purchaseItems.add(new PurchaseItem("bottle of perfume", 1, 47.50f, true, CategoryEnum.OTHER));
        when(purchaseParserService.parseAndCheckPurchases(purchases)).thenReturn(purchaseItems);

        // When
        String result = receiptService.createReceipt(purchases);

        // Then
        String expected = "1 imported box of chocolates: 10.50\n1 imported bottle of perfume: 54.65\nSales Taxes: 7.65 Total: 65.15";
        assertEquals(expected, result);
    }
}
