package com.example.order_service.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String productId;

    private String productName;

    private String productDescription;

    private float productPrice;

}
