package com.example.sales_taxes.service;

import com.example.sales_taxes.dto.PurchaseItem;
import com.example.sales_taxes.exception.BadRequestException;
import com.example.sales_taxes.utils.CategoryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.sales_taxes.utils.Constant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseParserService {

    private final PurchaseItemClassifierService purchaseItemClassifierService;

    public List<PurchaseItem> parseAndCheckPurchases(String purchases) {
        log.info("Parsing and checking purchases");
        List<PurchaseItem> items = new ArrayList<>();
        Pattern pattern = Pattern.compile(ITEM_PURCHASED_PATTERN);
        Matcher matcher = pattern.matcher(purchases);

        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            boolean isImported = determineIfImported(matcher.group(3), matcher.group(2));
            String name = extractItemName(matcher.group(3));
            float price = Float.parseFloat(matcher.group(4));
            validatePrice(price, name);

            CategoryEnum categoryEnum = purchaseItemClassifierService.getCategory(name);

            PurchaseItem item = new PurchaseItem(name, quantity, price, isImported, categoryEnum);
            items.add(item);
        }

        if (items.isEmpty()) {
            throw new BadRequestException("No purchase items found. Please check the format and try again.");
        }

        log.info("Parsed and checked purchases successfully");
        return items;
    }

    private boolean determineIfImported(String itemName, String itemImported) {
        return itemName.contains(IMPORTED_PATTERN) || itemImported != null;
    }

    private String extractItemName(String itemName) {
        if (itemName.contains(IMPORTED_PATTERN)) {
            return itemName.replace(IMPORTED_PATTERN+SPACE, EMPTY_STRING);
        }
        return itemName;
    }

    private void validatePrice(float price, String itemName) {
        if (price <= 0) {
            throw new BadRequestException(String.format("The price of %s cannot be negative or null", itemName));
        }
    }
}
