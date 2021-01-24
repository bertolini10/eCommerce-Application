package com.example.demo.controllers;

import com.example.demo.MyUTILITIES;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private UserController userController;

    @Before
    public void setup(){
        userController = new UserController();
        MyUTILITIES.injectObjects(userController, "userRepository", userRepository);
        MyUTILITIES.injectObjects(userController, "cartRepository", cartRepository);
        MyUTILITIES.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void findByUserID_FAIL() {
        ResponseEntity<User> response = userController.findById(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByUserID_OK() {
        User user = new User();
        user.setUsername("cris");
        user.setId(2L);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName(user.getUsername());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User userOK = response.getBody();
        assertNotNull(userOK);
        assertEquals(user.getId(), userOK.getId());
    }

    @Test
    public void findByUserName_FAIL() {
        ResponseEntity<User> response = userController.findByUserName("aston martin");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByUserName_OK() {
        User user = new User();
        user.setUsername("cris");
        user.setId(2L);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName(user.getUsername());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User userOK = response.getBody();
        assertNotNull(userOK);
        assertEquals(user.getUsername(), userOK.getUsername());
    }

    @Test
    public void UserCreation_OK() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("master");
        userRequest.setConfirmPassword("master");
        ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void UserCreation_FAIL() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("master");
        userRequest.setConfirmPassword("master1");
        ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
