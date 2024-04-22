package com.example.sales_taxes.service;

import com.example.sales_taxes.dto.PurchaseItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import static com.example.sales_taxes.utils.Constant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptService {

    private final PurchaseParserService purchaseParserService;


    public String createReceipt(String purchases) {
        log.info("Creating receipt for purchases: {}", purchases);
        List<PurchaseItem> purchaseItems = purchaseParserService.parseAndCheckPurchases(purchases);
        DecimalFormat df = createDecimalFormat();

        StringBuilder receiptBuilder = new StringBuilder();

        float totalSalesTax = 0.0f;
        float totalAmount = 0.0f;

        for (PurchaseItem item : purchaseItems) {
            log.info("Processing {} item: {} with the category: {}", item.getQuantity(), item.getName(), item.getCategory());
            float itemPrice = item.getPrice();
            float taxes;
            taxes= calculateTaxes(itemPrice, item.isImported(),item.getCategory().isExempt());
            float totalItemPrice = item.getQuantity() *(itemPrice + taxes);

            totalSalesTax += taxes;
            totalAmount += totalItemPrice;

            String importedPrefix = item.isImported() ? IMPORTED_PATTERN+SPACE : EMPTY_STRING;
            String formattedItemPrice = df.format(totalItemPrice);
            receiptBuilder.append(item.getQuantity())
                    .append(SPACE)
                    .append(importedPrefix)
                    .append(item.getName())
                    .append(COLON)
                    .append(SPACE)
                    .append(formattedItemPrice)
                    .append(LINE_SEPARATOR);

        }
        String formattedTotalSalesTax = df.format(roundSalesTax(totalSalesTax));
        String formattedTotalAmount = df.format(totalAmount);
        receiptBuilder.append("Sales Taxes: ").append(formattedTotalSalesTax).append(" Total: ").append(formattedTotalAmount);
        String receipt = receiptBuilder.toString();
        log.info("Created receipt: {}", receipt);
        return receipt;
    }

    private DecimalFormat createDecimalFormat() {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00", decimalFormatSymbols);
    }

    private float calculateTaxes(float price, boolean isImported, boolean isExempt) {
        float taxRate = isExempt ? 0.0f : SALES_TAX_RATE;
        if (isImported) {
            taxRate += IMPORT_TAX_RATE;
        }
        float salesTax = price * taxRate;
        return roundSalesTax(salesTax);
    }


    private float roundSalesTax(float salesTax) {
        return (float) (Math.ceil(salesTax / TAX_ROUNDED) * TAX_ROUNDED);
    }
}
