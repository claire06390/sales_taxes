package com.example.sales_taxes.service;

import com.example.sales_taxes.utils.CategoryEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseItemClassifierService {

    private final Map<String, CategoryEnum> keywords;

    public PurchaseItemClassifierService() {
        this.keywords = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        keywords.put("book", CategoryEnum.BOOK);
        keywords.put("chocolate", CategoryEnum.FOOD);
        keywords.put("chocolates", CategoryEnum.FOOD);
        keywords.put("pills", CategoryEnum.MEDICAL_PRODUCT);
    }

    public CategoryEnum getCategory(String name) {
        for (Map.Entry<String, CategoryEnum> entry : keywords.entrySet()) {
            if (name.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return CategoryEnum.OTHER;
    }
}
