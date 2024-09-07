package com.eshoppers.service;

import com.eshoppers.domain.User;
import com.eshoppers.dto.ShippingAddressDTO;

public interface OrderService {
    void processOrder(ShippingAddressDTO shippingAddressDTO, User currentUser);
}
