package com.example.notecollecter.service;

import com.example.notecollecter.dto.UserStatus;
import com.example.notecollecter.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    UserStatus getSelectedUser(String userId);
    List<UserDTO> getAllUsers();
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);

    UserDetailsService userDetailsService();
}
