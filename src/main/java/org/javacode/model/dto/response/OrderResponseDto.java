package org.javacode.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import org.javacode.model.entity.Status;
import org.javacode.model.view.Views;

import java.util.List;

@JsonView(Views.UserDetails.class)
public record OrderResponseDto(
        Integer userId,
        List<ProductResponseDto> products,
        Double amount,
        Status status) {
}
