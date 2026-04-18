package com.example.product_service.service;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.entity.ProductEntity;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getAllproduct();

    ProductDto getProductById(String id);

    ProductDto editProductById(String id, ProductDto productDto);

    ProductDto patchProductById(String id, JsonPatch jsonPatch);

    ProductEntity applyPatch(JsonPatch jsonPatch, ProductEntity productEntity);
}
