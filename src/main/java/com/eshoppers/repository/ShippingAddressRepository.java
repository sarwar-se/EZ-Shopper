package com.eshoppers.repository;

import com.eshoppers.domain.ShippingAddress;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
}
