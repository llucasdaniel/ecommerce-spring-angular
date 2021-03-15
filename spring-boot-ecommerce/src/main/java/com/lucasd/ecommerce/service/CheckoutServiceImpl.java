package com.lucasd.ecommerce.service;

import com.lucasd.ecommerce.dao.CustomerRepository;
import com.lucasd.ecommerce.dto.Purchase;
import com.lucasd.ecommerce.dto.PurchaseResponse;
import com.lucasd.ecommerce.entity.Customer;
import com.lucasd.ecommerce.entity.Order;
import com.lucasd.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve order info from DTO
        Order order = purchase.getOrder();

        Customer customer = purchase.getCustomer();

        Customer customerDb = customerRepository.findByEmail(purchase.getCustomer().getEmail());
        if (Objects.nonNull(customerDb)) {
            customer = customerDb;
        }

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with BillingAddress and shipping Address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        customer.add(order);

        //save to the database
        customerRepository.save(customer);

        //return a response

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate random UUID
        return UUID.randomUUID().toString();
    }
}
