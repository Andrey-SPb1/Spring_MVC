package org.javacode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.model.dto.create.UserCreateEditDto;
import org.javacode.model.entity.Order;
import org.javacode.model.entity.Status;
import org.javacode.model.entity.User;
import org.javacode.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        loadTestData();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getUserSummary() throws Exception {
        mockMvc.perform(get("/api/v1/user/summary/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("firstname").value("Ivan"))
                .andExpect(jsonPath("lastname").value("Petrov"))
                .andExpect(jsonPath("email").value("ivan.petrov@gmail.com"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/user/details/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("firstname").value("Ivan"))
                .andExpect(jsonPath("lastname").value("Petrov"))
                .andExpect(jsonPath("email").value("ivan.petrov@gmail.com"))
                .andExpect(jsonPath("orders", hasSize(2)))
                .andExpect(jsonPath("orders[0].status").value("CANCELLED"));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getAllUserSummary() throws Exception {
        mockMvc.perform(get("/api/v1/user/summary/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].email").value("maria.ivanova@gmail.com"));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAllUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/user/details/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].email").value("maria.ivanova@gmail.com"))
                .andExpect(jsonPath("$[0].orders[0].status").value("CANCELLED"));
    }

    @Test
    void create() throws Exception {
        UserCreateEditDto user = new UserCreateEditDto("test", "test", "test@gmail.com");

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstname").value("test"));
    }

    @Test
    void updateWithInvalidParam() throws Exception {
        UserCreateEditDto user = new UserCreateEditDto("test", "test", "test@gmail.com");

        mockMvc.perform(put("/api/v1/user/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        UserCreateEditDto user =
                new UserCreateEditDto("Maria", "Smirnova", "maria.smirnova@gmail.com");

        mockMvc.perform(put("/api/v1/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("firstname").value("Maria"))
                .andExpect(jsonPath("lastname").value("Smirnova"))
                .andExpect(jsonPath("email").value("maria.smirnova@gmail.com"));
    }

    @Test
    void deleteTest() throws Exception {
        User user = User.builder()
                .firstname("test")
                .lastname("test")
                .email("test@test.com")
                .build();

        Integer userId = userRepository.save(user).getId();

        mockMvc.perform(delete("/api/v1/user/" + userId))
                .andExpect(status().isNoContent());

    }

    private void loadTestData() {
        if(userRepository.count() != 0) return;

        User user1 = User.builder()
                .firstname("Ivan")
                .lastname("Petrov")
                .email("ivan.petrov@gmail.com")
                .orders(List.of(new Order()))
                .build();
        User user2 = User.builder()
                .firstname("Maria")
                .lastname("Ivanova")
                .email("maria.ivanova@gmail.com")
                .build();

        List<Order> orders = List.of(
                Order.builder()
                        .user(user1)
                        .amount(1000.0)
                        .status(Status.CANCELLED)
                        .build(),
                Order.builder()
                        .user(user1)
                        .amount(300.0)
                        .status(Status.COMPLETED)
                        .build());
        user1.setOrders(orders);

        userRepository.saveAll(List.of(user1, user2));
    }
}