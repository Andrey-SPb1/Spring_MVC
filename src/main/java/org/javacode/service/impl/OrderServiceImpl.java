package org.javacode.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.mapper.OrderMapper;
import org.javacode.model.dto.response.OrderResponseDto;
import org.javacode.repository.OrderRepository;
import org.javacode.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Optional<OrderResponseDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::map);
    }
}
