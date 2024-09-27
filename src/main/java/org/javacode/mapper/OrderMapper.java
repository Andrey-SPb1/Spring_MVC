package org.javacode.mapper;

import lombok.RequiredArgsConstructor;
import org.javacode.model.dto.response.OrderResponseDto;
import org.javacode.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderResponseDto> {

    private final ProductMapper productMapper;

    @Override
    public OrderResponseDto map(Order order) {
        return new OrderResponseDto(
                order.getUser().getId(),
                order.getProducts().stream()
                        .map(productMapper::map)
                        .toList(),
                order.getAmount(),
                order.getStatus()
        );
    }
}
