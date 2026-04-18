package com.example.product_service.controller;

import com.example.product_service.dto.ImageUploadResponseDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.service.*;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CloudService cloudinaryService;

    private final ProductAiService productAiService;

    @PostMapping("/upload-image")
    ResponseEntity<ImageUploadResponseDto> uploadProductImage(@RequestParam MultipartFile productImageFile) throws IOException {

        return ResponseEntity.ok(cloudinaryService.uploadProductImage(productImageFile));
    }

    @PostMapping("/add-product")
    ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        ProductDto product = productService.addProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    ResponseEntity<List<ProductDto>> getAllproduct(){
        return ResponseEntity.ok(productService.getAllproduct());
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> editProductById(@PathVariable String id, @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.editProductById(id,productDto));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ProductDto> patchProductById(
            @PathVariable String id,
            @RequestBody JsonPatch jsonPatch){
        return ResponseEntity.ok(productService.patchProductById(id,jsonPatch));
    }

    @PostMapping("/{id}/query")
    String queryAboutProduct(
            @RequestParam String customerQuery,
            @PathVariable String id ){

        return productAiService.queryAboutProduct(id,customerQuery);

    }

}
