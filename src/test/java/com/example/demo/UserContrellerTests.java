package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

public class UserContrellerTests {

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
    public void findByUserName_FAIL() {
        ResponseEntity<User> response = userController.findByUserName("aston martin");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
