package com.example.product_service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${media.cloudinary.cloud-name}")
    String cloudName;

    @Value("${media.cloudinary.api-key}")
    String cloudinaryApiKey;

    @Value("${media.cloudinary.api-secret}")
    String cloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloudName,
                "api_key",cloudinaryApiKey,
                "api_secret",cloudinaryApiSecret,
                "secure",true));
    }
}
