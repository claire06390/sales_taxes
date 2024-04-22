package com.example.sales_taxes.controller.declaration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Root Controller", description = "Controller guide to the root of the application")
public interface RootControllerDeclaration {

    @GetMapping
    @Operation(summary = "Get application root", responses = {
            @ApiResponse(responseCode = "200", description = "The application usage was returned successfully"),
    })
    ResponseEntity<String> root();

}
