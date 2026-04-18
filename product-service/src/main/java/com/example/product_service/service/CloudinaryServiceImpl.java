package com.example.product_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.product_service.dto.ImageUploadResponseDto;
import com.example.product_service.exception.ImageUploadException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudService{

    final Cloudinary cloudinary;

    public ImageUploadResponseDto uploadProductImage(MultipartFile productImageFile) {

        try {
            ImageUploadResponseDto imageUploadResponseDto = new ImageUploadResponseDto();
            Map params1 = ObjectUtils.asMap(
                    "use_filename", true,
                    "use_uniquename", false,
                    "overwrite", true);

            Map cloudinaryResponseMap = cloudinary.uploader().upload(productImageFile.getBytes(), params1);
            System.out.println(cloudinaryResponseMap);
            imageUploadResponseDto.setProductImageUrl((String) cloudinaryResponseMap.get("secure_url"));
            imageUploadResponseDto.setProductImageId((String) cloudinaryResponseMap.get("public_id"));
            return imageUploadResponseDto;
        }catch (IOException e){
            throw new ImageUploadException("Invalid Image");
        }
    }
}
