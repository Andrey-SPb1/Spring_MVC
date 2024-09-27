package org.javacode.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import org.javacode.model.view.Views;

import java.util.List;

public record UserResponseDto(
        @JsonView(Views.UserSummary.class)
        String firstname,
        @JsonView(Views.UserSummary.class)
        String lastname,
        @JsonView(Views.UserSummary.class)
        String email,
        @JsonView(Views.UserDetails.class)
        List<OrderResponseDto> orders) {

}
