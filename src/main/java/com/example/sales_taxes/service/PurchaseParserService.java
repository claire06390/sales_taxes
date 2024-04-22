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

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseParserService {

    private final PurchaseItemClassifierService purchaseItemClassifierService;

    public List<PurchaseItem> parseAndCheckPurchases(String purchases) {
        log.info("Parsing and checking purchases");
        List<PurchaseItem> items = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)\\s+(imported\\s+)?([\\w\\s]+)\\s+at\\s+(\\d+\\.\\d+)");

        Matcher matcher = pattern.matcher(purchases);

        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            boolean isImported = matcher.group(2) != null;
            String name = matcher.group(3);
            float price = Float.parseFloat(matcher.group(4));
            if(price <= 0) {
                throw new BadRequestException(String.format("The price of %s cannot be negative or null", name));
            }
            CategoryEnum categoryEnum = purchaseItemClassifierService.getCategory(name);

            PurchaseItem item = new PurchaseItem(name,quantity, price, isImported, categoryEnum);
            items.add(item);
        }
        if(items.isEmpty()) {
            throw new BadRequestException("No purchase items found the format of your input is incorrect. Please check the format and try again");
        }
        log.info("Parsed and checked purchases successfully");
        return items;
    }
}
