package org.javacode.mapper;

import lombok.RequiredArgsConstructor;
import org.javacode.model.dto.response.UserResponseDto;
import org.javacode.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResponseDto> {

    private final OrderMapper orderMapper;

    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getOrders().stream()
                        .map(orderMapper::map)
                        .toList()
        );
    }
}
