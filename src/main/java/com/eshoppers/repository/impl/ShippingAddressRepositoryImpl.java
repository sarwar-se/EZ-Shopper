package com.eshoppers.repository.impl;

import com.eshoppers.domain.ShippingAddress;
import com.eshoppers.repository.ShippingAddressRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {
    private static final Set<ShippingAddress> SHIPPING_ADDRESSES = new CopyOnWriteArraySet<>();

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        SHIPPING_ADDRESSES.add(shippingAddress);
        return shippingAddress;
    }

    @Override
    public Optional<ShippingAddress> findById(long shippingAddressId) {
        return SHIPPING_ADDRESSES
                .stream()
                .filter(shippingAddress -> shippingAddress.getId().equals(shippingAddressId))
                .findFirst();
    }
}
