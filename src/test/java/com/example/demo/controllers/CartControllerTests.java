package com.example.demo.controllers;

import com.example.demo.MyUTILITIES;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTests {
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartController cartController;

    @Before
    public void setup(){
        cartController = new CartController();
        MyUTILITIES.injectObjects(cartController, "userRepository", userRepository);
        MyUTILITIES.injectObjects(cartController, "cartRepository", cartRepository);
        MyUTILITIES.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void addToCart_OK(){
        Cart emptyCart = new Cart();
        User user = new User();
        user.setUsername("cris");
        user.setCart(emptyCart);
        Item item = new Item();
        item.setName("milk");
        item.setPrice(new BigDecimal(1));
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addToCart_user_FAIL(){
        Cart emptyCart = new Cart();
        Item item = new Item();
        item.setName("milk");
        item.setPrice(new BigDecimal(1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        modifyCartRequest.setUsername("crissssss");
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addToCart_item_FAIL(){
        Cart emptyCart = new Cart();
        User user = new User();
        user.setUsername("cris");
        user.setCart(emptyCart);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void removeFromCart_OK() {
        Cart emptyCart = new Cart();
        User user = new User();
        user.setUsername("cris");
        user.setCart(emptyCart);
        Item item = new Item();
        item.setName("milk");
        item.setPrice(new BigDecimal(1));
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void removeFromCart_user_FAIL() {
        Cart emptyCart = new Cart();
        Item item = new Item();
        item.setName("milk");
        item.setPrice(new BigDecimal(1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void removeFromCart_item_FAIL() {
        Cart emptyCart = new Cart();
        User user = new User();
        user.setUsername("cris");
        user.setCart(emptyCart);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(10);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


}
