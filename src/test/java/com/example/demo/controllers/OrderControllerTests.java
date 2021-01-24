package com.example.demo.controllers;

import com.example.demo.MyUTILITIES;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTests {

    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private OrderController orderController;

    @Before
    public void setup(){
        orderController = new OrderController();
        MyUTILITIES.injectObjects(orderController, "userRepository", userRepository);
        MyUTILITIES.injectObjects(orderController, "orderRepository", orderRepository);

    }

    @Test
    public void submitOrder_User_FAIL() {
        ResponseEntity<UserOrder> responseEntity = orderController.submit("");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void submitOrder_OK() {
        Item item = new Item();
        item.setDescription("milk");
        item.setId(2L);
        item.setPrice(BigDecimal.valueOf(1));
        Cart cart = new Cart();
        cart.addItem(item);
        User user = new User();
        user.setId(1L);
        user.setUsername("cris");
        user.setCart(cart);
        cart.setUser(user);

        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ResponseEntity<UserOrder> responseEntity = orderController.submit(user.getUsername());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserOrder order = responseEntity.getBody();
        assertNotNull(order);
        assertEquals(user.getUsername(), order.getUser().getUsername());
    }

    @Test
    public void getOrdersUser_OK() {
        List<UserOrder> orders = new ArrayList<UserOrder>();
        Item item = new Item();
        item.setDescription("milk");
        item.setId(1L);
        User user = new User();
        user.setUsername("cris");
        user.setId(1L);
        UserOrder order = new UserOrder();
        order.setId(20L);
        order.setItems(Collections.singletonList(item));
        order.setUser(user);

        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        when(orderRepository.findByUser(any(User.class))).thenReturn(orders);
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("username");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UserOrder> orderList = responseEntity.getBody();
        assertNotNull(orderList);
        assertEquals(orders.size(), orderList.size());

    }

}
