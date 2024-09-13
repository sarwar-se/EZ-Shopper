package com.eshoppers.repository;

import com.eshoppers.domain.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);

    Optional<ShippingAddress> findById(long shippingAddressId);
}
