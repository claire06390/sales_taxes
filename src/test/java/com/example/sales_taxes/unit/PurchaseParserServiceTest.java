package com.example.sales_taxes.unit;

import com.example.sales_taxes.dto.PurchaseItem;
import com.example.sales_taxes.exception.BadRequestException;
import com.example.sales_taxes.service.PurchaseItemClassifierService;
import com.example.sales_taxes.service.PurchaseParserService;
import com.example.sales_taxes.utils.CategoryEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseParserServiceTest {

    @Mock
    private PurchaseItemClassifierService purchaseItemClassifierService;

    @InjectMocks
    private PurchaseParserService purchaseParserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parseAndCheckPurchases_ValidInput_ReturnsCorrectItems() {
        // Given
        String purchases = "1 book at 12.49\n1 music CD at 14.99\n1 chocolate bar at 0.85";

        // Mock the behavior of the PurchaseItemClassifierService
        when(purchaseItemClassifierService.getCategory(anyString())).thenReturn(CategoryEnum.BOOK);

        // When
        List<PurchaseItem> purchaseItems = purchaseParserService.parseAndCheckPurchases(purchases);

        // Then
        assertNotNull(purchaseItems);
        assertEquals(3, purchaseItems.size());
        assertEquals("book", purchaseItems.get(0).getName());
        assertEquals("music CD", purchaseItems.get(1).getName());
        assertEquals("chocolate bar", purchaseItems.get(2).getName());
        assertEquals(12.49f, purchaseItems.get(0).getPrice());
        assertEquals(14.99f, purchaseItems.get(1).getPrice());
        assertEquals(0.85f, purchaseItems.get(2).getPrice());
        assertFalse(purchaseItems.get(0).isImported());
        assertFalse(purchaseItems.get(1).isImported());
        assertFalse(purchaseItems.get(2).isImported());
        assertEquals(CategoryEnum.BOOK, purchaseItems.get(0).getCategory());
        assertEquals(CategoryEnum.BOOK, purchaseItems.get(1).getCategory());
        assertEquals(CategoryEnum.BOOK, purchaseItems.get(2).getCategory());
    }

    @Test
    void parseAndCheckPurchases_InvalidInput_ThrowsBadRequestException() {
        // Given
        String purchases = "invalid input";

        // When/Then
        assertThrows(BadRequestException.class, () -> purchaseParserService.parseAndCheckPurchases(purchases));
    }

    @Test
    void parseAndCheckPurchases_InvalidInputPattern_ThrowsBadRequestException() {
        // Given
        String purchases = "1 book 12.49";

        // When/Then
        assertThrows(BadRequestException.class, () -> purchaseParserService.parseAndCheckPurchases(purchases));
    }


    @Test
    void parseAndCheckPurchases_InvalidInputNegativeAmount_ThrowsBadRequestException() {
        // Given
        String purchases = "1 book at -12.49";

        // When/Then
        assertThrows(BadRequestException.class, () -> purchaseParserService.parseAndCheckPurchases(purchases));
    }

    @Test
    void parseAndCheckPurchases_InvalidInputNullAmount_ThrowsBadRequestException() {
        // Given
        String purchases = "1 book at 0";

        // When/Then
        assertThrows(BadRequestException.class, () -> purchaseParserService.parseAndCheckPurchases(purchases));
    }
}