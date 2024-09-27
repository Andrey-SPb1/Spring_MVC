package org.javacode.controller;

import lombok.RequiredArgsConstructor;
import org.javacode.exception.ResourceNotFoundException;
import org.javacode.model.dto.response.OrderResponseDto;
import org.javacode.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderResponseDto findById(@PathVariable Integer id) {
        return orderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

}
