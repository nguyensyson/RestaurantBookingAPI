package com.poly.bookingapi.cloudinaryconfig;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "dmyc3p8g9";

    private final String API_KEY = "984915235993789";

    private final String API_SECRET = "ce0SCxjhUh1IpNrNiF-GMmj7yLQ";

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config  = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}
