package com.eshoppers.service.impl;

import com.eshoppers.domain.User;
import com.eshoppers.dto.LoginDTO;
import com.eshoppers.dto.UserDTO;
import com.eshoppers.exceptions.UserNotFoundException;
import com.eshoppers.repository.UserRepository;
import com.eshoppers.annotation.JDBC;
import com.eshoppers.annotation.Sha256;
import com.eshoppers.security.PasswordEncryption;
import com.eshoppers.service.UserService;
import jakarta.inject.Inject;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncryption passwordEncryption;

    @Inject
    public UserServiceImpl(@JDBC UserRepository userRepository, @Sha256 PasswordEncryption passwordEncryption) {
        this.userRepository = userRepository;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncryption.encrypt(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        userRepository.save(user);
    }

    @Override
    public boolean isNotUniqueUsername(UserDTO user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public boolean isNotUniqueEmail(UserDTO user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    @Override
    public User verifyUser(LoginDTO loginDTO) throws UserNotFoundException {
        var user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found by " + loginDTO.getUsername()));

        var encryptedPassword = passwordEncryption.encrypt(loginDTO.getPassword());
        if (user.getPassword().equals(encryptedPassword)) {
            return user;
        } else {
            throw new UserNotFoundException("Incorrect username password");
        }
    }
}
