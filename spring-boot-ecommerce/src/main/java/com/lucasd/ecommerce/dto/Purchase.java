package com.lucasd.ecommerce.dto;

import com.lucasd.ecommerce.entity.Address;
import com.lucasd.ecommerce.entity.Customer;
import com.lucasd.ecommerce.entity.Order;
import com.lucasd.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
