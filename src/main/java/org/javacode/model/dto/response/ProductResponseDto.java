package org.javacode.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import org.javacode.model.view.Views;

@JsonView(Views.UserDetails.class)
public record ProductResponseDto(
        String name,
        String description,
        Double price) {
}
