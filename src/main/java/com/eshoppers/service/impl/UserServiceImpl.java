package com.eshoppers.service.impl;

import com.eshoppers.domain.User;
import com.eshoppers.dto.LoginDTO;
import com.eshoppers.dto.UserDTO;
import com.eshoppers.exceptions.UserNotFoundException;
import com.eshoppers.repository.UserRepository;
import com.eshoppers.service.UserService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encryptPassword(userDTO.getPassword()));
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

        var encryptedPassword = encryptPassword(loginDTO.getPassword());
        if (user.getPassword().equals(encryptedPassword)) {
            return user;
        } else {
            throw new UserNotFoundException("Incorrect username password");
        }
    }

    private String encryptPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
