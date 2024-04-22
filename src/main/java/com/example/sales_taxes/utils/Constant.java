package com.example.sales_taxes.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Constant {

    // TYPES FOR SWAGGER
    public static final String STRING_TYPE = "string";

    public static final String DATE_FORMAT = "date-time";


    // STRING CONSTANT
    public static final String LINE_SEPARATOR = "\n";
    public static final String SPACE = " ";
    public static final String EMPTY_STRING = "";
    public static final String COLON = ":";

    // PATTERN
    public static final String ITEM_PURCHASED_PATTERN = "(\\d+)\\s+(imported\\s+)?([\\w\\s]+)\\s+at\\s+(\\d+\\.\\d+)";

    public static final String IMPORTED_PATTERN = "imported";

    // TAXES RATES
    public static final float SALES_TAX_RATE = 0.1f;
    public static final float IMPORT_TAX_RATE = 0.05f;
    public static final float TAX_ROUNDED = 0.05f;


}
