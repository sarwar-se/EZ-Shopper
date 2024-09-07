package com.eshoppers.service;

import com.eshoppers.domain.User;
import com.eshoppers.dto.LoginDTO;
import com.eshoppers.dto.UserDTO;
import com.eshoppers.exceptions.UserNotFoundException;

public interface UserService {
    void saveUser(UserDTO userDTO);

    boolean isNotUniqueUsername(UserDTO user);

    boolean isNotUniqueEmail(UserDTO user);

    User verifyUser(LoginDTO loginDTO) throws UserNotFoundException;
}
