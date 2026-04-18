package com.example.product_service.service;

import com.example.product_service.dto.ImageUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

    ImageUploadResponseDto uploadProductImage(MultipartFile productImageFile);

}
