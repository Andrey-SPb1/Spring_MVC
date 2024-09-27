package org.javacode.service;

import org.javacode.model.dto.response.OrderResponseDto;

import java.util.Optional;

public interface OrderService {

    Optional<OrderResponseDto> findById(Integer id);
}
