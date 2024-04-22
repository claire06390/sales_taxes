package com.example.sales_taxes.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryEnum {

    FOOD(true),BOOK(true),MEDICAL_PRODUCT(true),OTHER(false);

    private boolean exempt;
}
