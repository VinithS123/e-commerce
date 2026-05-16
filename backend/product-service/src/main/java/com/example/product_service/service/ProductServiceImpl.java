package com.example.product_service.service;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.entity.ProductEntity;
import com.example.product_service.exception.InvalidPatchException;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    final ProductRepository productRepository;

    final ProductMapper productMapper;

    public ProductDto addProduct(ProductDto productDto) {

        ProductEntity productEntity = productMapper.toProductEntity(productDto);
        return productMapper.toProductDto(productRepository.save(productEntity));

    }

    public List<ProductDto> getAllproduct() {
        return productMapper.toProductDtoList(productRepository.findAll());
    }

    public ProductDto getProductById(String id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("product not found"));
        return productMapper.toProductDto(product);
    }

    public ProductDto editProductById(String id, ProductDto productDto) {

        ProductEntity productEntity = productMapper.toProductEntity(getProductById(id));
        return productMapper.toProductDto(productRepository.save(productEntity));

    }

    public ProductDto patchProductById(String id, JsonPatch jsonPatch){

            ProductDto productDto = getProductById(id);
            ProductEntity productEntity = applyPatch(jsonPatch, productMapper.toProductEntity(productDto));
            return productMapper.toProductDto(productRepository.save(productEntity));
    }

    public ProductEntity applyPatch(JsonPatch jsonPatch, ProductEntity productEntity){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JsonNode customerNode = objectMapper.convertValue(productEntity, JsonNode.class);
            JsonNode patchNode = jsonPatch.apply(customerNode);
            return objectMapper.treeToValue(patchNode, ProductEntity.class);
        }
        catch (JsonPatchException | JsonProcessingException e){
            throw new InvalidPatchException("json patch exception occoured ");
        }

    }
}
