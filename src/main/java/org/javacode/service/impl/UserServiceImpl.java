package org.javacode.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.mapper.UserMapper;
import org.javacode.mapper.create.UserCreateEditMapper;
import org.javacode.model.dto.create.UserCreateEditDto;
import org.javacode.model.dto.response.UserResponseDto;
import org.javacode.model.entity.User;
import org.javacode.repository.UserRepository;
import org.javacode.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    @Override
    public Optional<UserResponseDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::map);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public UserResponseDto create(UserCreateEditDto user) {
        return Optional.of(user)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<UserResponseDto> update(Integer id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(user -> userCreateEditMapper.map(userDto, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::map);
    }

    @Override
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.deleteById(id);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
