package com.example.sales_taxes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.sales_taxes.utils.Constant.STRING_TYPE;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PurchaseDtoIn {

    @NotEmpty
    @Schema(name = "purchases", description = "List of items purchased", type = STRING_TYPE, example = "1 book at 12.49\n1 music CD at 14.99")
    private String purchases;
}
