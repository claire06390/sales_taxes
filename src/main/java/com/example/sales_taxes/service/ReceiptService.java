package com.example.sales_taxes.service;

import com.example.sales_taxes.dto.PurchaseItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.example.sales_taxes.utils.Constant.LINE_SEPARATOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptService {
    @Value("${application.sales.tax.rate}")
    private Float salesTaxRate;

    @Value("${application.import.tax.rate}")
    private Float importTaxRate;

    @Value("${application.tax.rounded}")
    private Float taxRounded;

    private final PurchaseParserService purchaseParserService;

    public String createReceipt(String purchases) {
        log.info("Creating receipt for purchases: {}", purchases);
        List<PurchaseItem> purchaseItems = purchaseParserService.parseAndCheckPurchases(purchases);

        StringBuilder receiptBuilder = new StringBuilder();

        float totalSalesTax = 0.0f;
        float totalAmount = 0.0f;

        for (PurchaseItem item : purchaseItems) {
            float itemPrice = item.getPrice();
            float salesTax = 0.0f;
            if(item.getCategory().isExempt()) {
                log.info("Item {} is exempt from sales tax", item.getName());
            }else{
                salesTax = calculateSalesTax(itemPrice, item.isImported());
                log.info("Item {} is not exempt from sales tax and the taxe represent {}", item.getName(), salesTax);
            }
            float totalItemPrice = itemPrice + salesTax;

            totalSalesTax += salesTax;
            totalAmount += item.getQuantity() * totalItemPrice;

            receiptBuilder.append(item.getName()).append(": ").append(totalItemPrice).append(LINE_SEPARATOR);
        }
        receiptBuilder.append("Sales Taxes: ").append(roundSalesTax(totalSalesTax)).append(" Total: ").append(totalAmount);
        String receipt = receiptBuilder.toString();
        log.info("Created receipt: {}", receipt);
        return receipt;
    }

    private float calculateSalesTax(float price, boolean isImported) {
        float taxRate = salesTaxRate;
        if (isImported) {
            taxRate += importTaxRate;
        }
        float salesTax = price * taxRate;
        return roundSalesTax(salesTax);
    }

    private float roundSalesTax(float salesTax) {
        return (float) (Math.ceil(salesTax / taxRounded) * taxRounded);
    }
}
