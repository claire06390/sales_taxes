package com.example.sales_taxes.controller.declaration;


import com.example.sales_taxes.dto.PurchaseDtoIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Purchase Controller", description = "Controller handling purchase and return receipt details")
public interface PurchaseControllerDeclaration {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Return receipt details of purchase", responses = {
            @ApiResponse(responseCode = "200", description = "Receipt details was successfully retuned"),
            @ApiResponse(responseCode = "400", description = "Purchase information incorrect"),
    })
    ResponseEntity<String> makePurchase(@Parameter(in = ParameterIn.DEFAULT, description = "List of items purchased", required = true)
                                      @RequestBody @Valid PurchaseDtoIn purchases);

}
