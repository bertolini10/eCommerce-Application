package com.example.demo.controllers;

import com.example.demo.MyUTILITIES;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {

    private ItemRepository itemRepository = mock(ItemRepository.class);
    private ItemController itemController;

    @Before
    public void setup(){
        itemController = new ItemController();
        MyUTILITIES.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItemsByID_FAIL() {
        ResponseEntity<Item> response = itemController.getItemById(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getItemsByID_OK() {
        Item item = new Item();
        item.setDescription("milk");
        item.setId(2L);
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(item.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item itemOK = response.getBody();
        assertNotNull(itemOK);
        assertEquals(itemOK.getId(), itemOK.getId());
    }

    @Test
    public void getItemsByNAME_FAIL() {
        ResponseEntity<List<Item>> response = itemController.getItemsByName("");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getItemsByNAME_OK(){
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setName("milk");
        item.setId(1L);
        items.add(item);


        when(itemRepository.findByName(anyString())).thenReturn(items);
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("milk");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Item> itemsList = responseEntity.getBody();
        Assert.assertNotNull(itemsList);
        Assert.assertEquals(items.size(), itemsList.size());

    }

    @Test
    public void getItemsByName_Empty_FAIL() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("");
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getAllItems() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}

