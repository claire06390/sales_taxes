package com.example.sales_taxes.controller.implementation;

import com.example.sales_taxes.dto.PurchaseDtoIn;
import com.example.sales_taxes.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sales_taxes.controller.declaration.PurchaseControllerDeclaration;

import static com.example.sales_taxes.utils.RouteConstants.ActionRoutes.BASE_PURCHASE_ROUTE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PURCHASE_ROUTE)
@Validated
public class PurchaseControllerImplementation implements PurchaseControllerDeclaration {

    private final ReceiptService receiptService;

    public ResponseEntity<String> makePurchase(PurchaseDtoIn purchases) {
        String receipt = receiptService.createReceipt(purchases.getPurchases());
        return ResponseEntity.ok(receipt);
    }

}
