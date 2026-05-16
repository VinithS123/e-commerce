package com.example.product_service.mapper;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(ProductEntity productEntity);

    ProductEntity toProductEntity(ProductDto productDto);

    List<ProductDto> toProductDtoList(List<ProductEntity> productEntityList);

}
