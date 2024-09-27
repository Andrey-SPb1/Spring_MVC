package org.javacode.mapper;

import org.javacode.model.dto.response.ProductResponseDto;
import org.javacode.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductResponseDto>{
    @Override
    public ProductResponseDto map(Product product) {
        return new ProductResponseDto(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
