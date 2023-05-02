package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController,"cartRepository", cartRepository);
        TestUtils.injectObjects(cartController,"itemRepository", itemRepository);
    }

    @Test
    public void testCreateCart() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        when(userRepository.findByUsername("test")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(1L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCreateCartBadItem() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        when(userRepository.findByUsername("test")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(2L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateCartBadUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        when(userRepository.findByUsername("test2")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(1L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCart() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        testUser.getCart().addItem(item);

        when(userRepository.findByUsername("test")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(1L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, testUser.getCart().getItems().size());
    }

    @Test
    public void testRemoveFromCartBadItem() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        testUser.getCart().addItem(item);

        when(userRepository.findByUsername("test")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(2L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(1, testUser.getCart().getItems().size());
    }

    @Test
    public void testRemoveFromCartBadUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(0);
        testUser.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        testUser.getCart().addItem(item);

        when(userRepository.findByUsername("test2")).thenReturn(testUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(2L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(1, testUser.getCart().getItems().size());
    }
}
