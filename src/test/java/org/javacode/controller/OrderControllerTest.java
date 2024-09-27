package org.javacode.controller;

import lombok.RequiredArgsConstructor;
import org.javacode.model.entity.Order;
import org.javacode.model.entity.Status;
import org.javacode.model.entity.User;
import org.javacode.repository.OrderRepository;
import org.javacode.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
class OrderControllerTest {

    private final MockMvc mockMvc;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @BeforeEach
    void setUp() {
        loadTestData();
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/order/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("amount").value(1200.00))
                .andExpect(jsonPath("status").value("COMPLETED"))
                .andExpect(jsonPath("userId").value(1));
    }

    @Test
    void findByWrongId() throws Exception {
        mockMvc.perform(get("/api/v1/order/11"))
                .andExpect(status().isNotFound());
    }

    private void loadTestData() {
        if (orderRepository.count() != 0) return;

        User user = userRepository.save(User.builder()
                .firstname("Semen")
                .lastname("Semenov")
                .email("semenov@gmail.com")
                .build());

        Order order = Order.builder()
                .user(user)
                .amount(1200.0)
                .status(Status.COMPLETED)
                .build();

        orderRepository.save(order);
    }
}