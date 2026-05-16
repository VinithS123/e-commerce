package com.example.product_service.service;

import com.example.product_service.entity.ProductEntity;
import com.example.product_service.mapper.ProductMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductAiServiceImpl implements ProductAiService {

    final ProductService productService;

    final ProductMapper productMapper;

    final Client client;

    @Override
    public String queryAboutProduct(String id, String customerQuery) {

        ProductEntity product = productMapper.toProductEntity(productService.getProductById(id));

        String productQueryPrompt = "\"You are a helpful assistant for an e-commerce store. \""
                +"Here is the product Info : \n"
                +"Product Name : " + product.getProductName()+"\n"
                +"Product Price : "+ product.getProductPrice()+"\n"
                +"Product Description:"+ product.getProductDescription()+"\n"
                +"Users Question: "+ customerQuery;

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        productQueryPrompt,
                        null);

        return response.text();

    }
}
