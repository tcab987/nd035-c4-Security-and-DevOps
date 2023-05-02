package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    final private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItems() {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(new Item()));
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    @Test
    public void testGetItemById() {
        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item returnedItem = response.getBody();

        assertNotNull(returnedItem);
        assertEquals(item, returnedItem);
    }

    @Test
    public void testGetItemsByName() {
        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        when(itemRepository.findByName(any())).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("widget");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> returnedItems = response.getBody();

        assertNotNull(returnedItems);
        assertEquals(item, returnedItems.get(0));
    }

    @Test
    public void testGetItemsByNameNotFound() {

        ResponseEntity<List<Item>> response = itemController.getItemsByName("widget");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        List<Item> returnedItems = response.getBody();

        assertNull(returnedItems);

    }
}
