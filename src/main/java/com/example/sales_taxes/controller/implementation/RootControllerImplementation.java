package com.example.sales_taxes.controller.implementation;

import com.example.sales_taxes.controller.declaration.RootControllerDeclaration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/root")
@Validated
public class RootControllerImplementation implements RootControllerDeclaration {

    @Override
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("This application allows you to obtain the details of an invoice receipt with taxes");
    }
}
