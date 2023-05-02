package com.example.demo.security;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class UserDetailsServiceImplTest {
    final private UserRepository userRepository = mock(UserRepository.class);

    private UserDetailsServiceImpl impl;

    @BeforeEach
    public void setUp() {
        impl = new UserDetailsServiceImpl();
        TestUtils.injectObjects(impl, "userRepository", userRepository);
    }

    @Test
    public void testLoadUserByUsername() {

    }

}
