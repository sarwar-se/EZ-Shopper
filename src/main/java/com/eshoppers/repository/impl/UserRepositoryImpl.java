package com.eshoppers.repository.impl;

import com.eshoppers.annotation.Local;
import com.eshoppers.domain.User;
import com.eshoppers.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Local
public class UserRepositoryImpl implements UserRepository {
    private static final Set<User> USERS = new CopyOnWriteArraySet<>();

    @Override
    public void save(User user) {
        USERS.add(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return USERS.stream()
                .filter(user -> Objects.equals(user.getUsername(), username)).findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return USERS.stream()
                .filter(user -> Objects.equals(user.getEmail(), email)).findFirst();
    }
}
