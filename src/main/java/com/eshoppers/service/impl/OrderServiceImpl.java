package com.eshoppers.service.impl;

import com.eshoppers.domain.Order;
import com.eshoppers.domain.ShippingAddress;
import com.eshoppers.domain.User;
import com.eshoppers.dto.ShippingAddressDTO;
import com.eshoppers.exceptions.CartItemNotFoundException;
import com.eshoppers.repository.CartRepository;
import com.eshoppers.repository.OrderRepository;
import com.eshoppers.repository.ShippingAddressRepository;
import com.eshoppers.annotation.JDBC;
import com.eshoppers.service.OrderService;
import jakarta.inject.Inject;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final CartRepository cartRepository;

    @Inject
    public OrderServiceImpl(@JDBC OrderRepository orderRepository,
                            @JDBC ShippingAddressRepository shippingAddressRepository,
                            @JDBC CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void processOrder(ShippingAddressDTO shippingAddressDTO, User currentUser) {
        var shippingAddress = convertTo(shippingAddressDTO);
        var savedShippingAddress = shippingAddressRepository.save(shippingAddress);
        var cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new CartItemNotFoundException("Cart not found by current user"));

        var order = new Order();
        order.setCart(cart);
        order.setShippingAddress(savedShippingAddress);
        order.setShipped(false);
        order.setUser(currentUser);

        orderRepository.save(order);
    }

    private ShippingAddress convertTo(ShippingAddressDTO shippingAddressDTO) {
        var shippingAddress = new ShippingAddress();

        shippingAddress.setAddress(shippingAddressDTO.getAddress());
        shippingAddress.setAddress2(shippingAddressDTO.getAddress2());
        shippingAddress.setCountry(shippingAddressDTO.getCountry());
        shippingAddress.setState(shippingAddressDTO.getState());
        shippingAddress.setZip(shippingAddressDTO.getZip());
        shippingAddress.setMobileNumber(shippingAddressDTO.getMobileNumber());

        return shippingAddress;
    }
}
