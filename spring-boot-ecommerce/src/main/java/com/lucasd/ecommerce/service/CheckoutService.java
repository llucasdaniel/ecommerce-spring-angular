package com.lucasd.ecommerce.service;

import com.lucasd.ecommerce.dto.Purchase;
import com.lucasd.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
