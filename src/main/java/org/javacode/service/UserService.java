package org.javacode.service;

import org.javacode.model.dto.create.UserCreateEditDto;
import org.javacode.model.dto.response.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserResponseDto> findById(Integer id);

    List<UserResponseDto> findAll();

    UserResponseDto create(UserCreateEditDto user);

    Optional<UserResponseDto> update(Integer id, UserCreateEditDto user);

    boolean delete(Integer id);
}
