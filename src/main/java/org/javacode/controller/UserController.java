package org.javacode.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.javacode.exception.ResourceNotFoundException;
import org.javacode.model.dto.create.UserCreateEditDto;
import org.javacode.model.dto.response.UserResponseDto;
import org.javacode.model.view.Views;
import org.javacode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/summary/{id}")
    @JsonView(Views.UserSummary.class)
    public UserResponseDto getUserSummary(@PathVariable Integer id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @GetMapping("/details/{id}")
    @JsonView(Views.UserDetails.class)
    public UserResponseDto getUserDetails(@PathVariable Integer id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @GetMapping("/summary/all")
    @JsonView(Views.UserSummary.class)
    public List<UserResponseDto> getAllUserSummary() {
        return userService.findAll();
    }

    @GetMapping("/details/all")
    @JsonView(Views.UserDetails.class)
    public List<UserResponseDto> getAllUserDetails() {
        return userService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.UserSummary.class)
    public UserResponseDto create(@Validated @RequestBody UserCreateEditDto user) {
        return userService.create(user);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.UserSummary.class)
    public UserResponseDto update(@PathVariable("id") Integer id,
                                  @Validated @RequestBody UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return userService.delete(id) ? noContent().build() : notFound().build();
    }
}
