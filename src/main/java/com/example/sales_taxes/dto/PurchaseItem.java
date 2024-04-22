package com.example.sales_taxes.dto;

import com.example.sales_taxes.utils.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseItem {

    private String name;
    private int quantity;
    private float price;
    private boolean imported;
    private CategoryEnum category;

    // ADD GITHUB PIPELINE
}
