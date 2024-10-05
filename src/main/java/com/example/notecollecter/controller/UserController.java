package com.example.notecollecter.controller;

import com.example.notecollecter.customStatusCodes.SelectedUserErrorStatus;
import com.example.notecollecter.dto.UserStatus;
import com.example.notecollecter.dto.impl.UserDTO;
import com.example.notecollecter.exception.DataPersistException;
import com.example.notecollecter.exception.UserNotFoundException;
import com.example.notecollecter.service.UserService;
import com.example.notecollecter.util.AppUtil;
import com.example.notecollecter.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic
    ) {
        //Todo: profilePic ---> Base64
        try {
            byte[] profilePicBytes = profilePic.getBytes();
            String base64ProfilePic = AppUtil.generateProPictoBase64(profilePicBytes);

            //Todo: Generate userId
            String userId = AppUtil.generateUserId();

            //Todo: Build the Object
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userDTO.setProfilePic(base64ProfilePic);

            //Todo: Save the Object
            userService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable("userId") String userId) {
        if (!userId.matches(Regex.USER_ID_REGEX)) {
            return new SelectedUserErrorStatus(1, "Invalid User Id");
        }
        return userService.getSelectedUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        if (!userId.matches(Regex.USER_ID_REGEX)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") String userId,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic)
    {
        String base64ProfilePic = null;
        try {
            byte[] profilePicBytes = profilePic.getBytes();
            base64ProfilePic = AppUtil.generateProPictoBase64(profilePicBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setProfilePic(base64ProfilePic);

        try {
            userService.updateUser(userId, userDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
