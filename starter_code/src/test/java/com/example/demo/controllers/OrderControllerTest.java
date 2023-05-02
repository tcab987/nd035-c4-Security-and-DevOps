package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    final private OrderRepository orderRepository = mock(OrderRepository.class);

    final private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
    }

    @Test
    public void testSubmit() {
        User user = new User();
        user.setUsername("test");

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        Cart cart = new Cart();
        cart.addItem(item);

        user.setCart(cart);

        when(userRepository.findByUsername("test")).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder userOrder = response.getBody();
        assertNotNull(userOrder);
        assertNotNull(userOrder.getItems());
        assertEquals(item, userOrder.getItems().get(0));

    }

    @Test
    public void testSubmitUserNotFound() {
        User user = new User();
        user.setUsername("test");

        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        Cart cart = new Cart();
        cart.addItem(item);

        user.setCart(cart);

        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser() {
        User user = new User();
        user.setUsername("test");


        Item item = new Item();
        item.setId(1L);
        item.setName("widget");
        item.setPrice(BigDecimal.valueOf(1.99));

        Cart cart = new Cart();
        cart.addItem(item);
        cart.setUser(user);

        user.setCart(cart);

        UserOrder order = UserOrder.createFromCart(cart);

        when(userRepository.findByUsername("test")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(Collections.singletonList(order));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }
}
