package com.lucasd.ecommerce.controller;

import com.lucasd.ecommerce.dto.Purchase;
import com.lucasd.ecommerce.dto.PurchaseResponse;
import com.lucasd.ecommerce.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping("/purchase")
    public PurchaseResponse savePurchase(@RequestBody Purchase purchase) {
        return this.checkoutService.placeOrder(purchase);
    }


}
