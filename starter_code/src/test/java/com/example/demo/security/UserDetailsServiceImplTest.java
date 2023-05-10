package com.example.demo.security;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsServiceImplTest {
    final private UserRepository userRepository = mock(UserRepository.class);

    private UserDetailsServiceImpl impl;

    @BeforeEach
    public void setUp() {
        impl = new UserDetailsServiceImpl();
        TestUtils.injectObjects(impl, "userRepository", userRepository);
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setId(1);
        testUser.setPassword("password");

        when(userRepository.findByUsername("test")).thenReturn(testUser);
        UserDetails details = impl.loadUserByUsername("test");

        assertNotNull(details);
        assertEquals(details.getUsername(), testUser.getUsername());

    }

    @Test
    public void testLoadUserByUsernameError() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> impl.loadUserByUsername("notfound")
        );
    }

}
