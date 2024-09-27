package org.javacode.mapper.create;

import org.javacode.mapper.Mapper;
import org.javacode.model.dto.create.UserCreateEditDto;
import org.javacode.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto user) {
        return User.builder()
                .email(user.email())
                .firstname(user.firstname())
                .lastname(user.lastname())
                .orders(new ArrayList<>())
                .build();
    }

    @Override
    public User map(UserCreateEditDto userDto, User user) {
        user.setEmail(userDto.email());
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        return user;
    }
}
