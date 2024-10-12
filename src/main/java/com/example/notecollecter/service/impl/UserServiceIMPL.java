package com.example.notecollecter.service.impl;

import com.example.notecollecter.customStatusCodes.SelectedUserErrorStatus;
import com.example.notecollecter.dao.UserDao;
import com.example.notecollecter.dto.UserStatus;
import com.example.notecollecter.dto.impl.UserDTO;
import com.example.notecollecter.entity.impl.UserEntity;
import com.example.notecollecter.exception.DataPersistException;
import com.example.notecollecter.exception.UserNotFoundException;
import com.example.notecollecter.service.UserService;
import com.example.notecollecter.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));
        if (savedUser == null) {
            throw new DataPersistException("User not saved");
        }
    }

    @Override
    public UserStatus getSelectedUser(String userId) {
        if (userDao.existsById(userId)) {
            UserEntity user = userDao.getReferenceById(userId);
            return mapping.toUserDTO(user);
        } else {
            return new SelectedUserErrorStatus(2, "User " + userId + " not found");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.toUserDTOs(userDao.findAll());
    }

    @Override
    public void deleteUser(String userId) {
        if (!userDao.existsById(userId)) {
            throw new UserNotFoundException("User " + userId + " not found");
        } else {
            userDao.deleteById(userId);
        }
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> search = userDao.findById(userId);
        if (search.isPresent()) {
            search.get().setEmail(userDTO.getEmail());
            search.get().setFirstName(userDTO.getFirstName());
            search.get().setLastName(userDTO.getLastName());
            search.get().setPassword(userDTO.getPassword());
            search.get().setProfilePic(userDTO.getProfilePic());
        } else {
            throw new UserNotFoundException("User " + userId + " not found");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userName ->
                userDao.findByEmail(userName)
                        .orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
}
