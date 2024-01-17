package com.poly.bookingapi.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class ImageUploader implements Runnable{
    private String cloudName = "darvug7fk";
    private String apiKey = "165153254544586";
    private String apiSecret= "SHswGN9UI8W8RmEFAoIKFMey4dM";
    private byte[] imageData;
    private Integer id;
    private final ProductRepository productRepository;

    public ImageUploader(byte[] imageData, Integer id, ProductRepository productRepository) {
        this.imageData = imageData;
        this.id = id;
        this.productRepository = productRepository;
    }

    @Override
    public void run() {
        try {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret));

    // Upload file lên Cloudinary
    Map uploadResult = cloudinary.uploader().upload(imageData, ObjectUtils.asMap(
            "folder", "BeesMeal"
    ));

    // Lấy URL của ảnh sau khi upload thành công
    String imageUrl = (String) uploadResult.get("secure_url");

    // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
    Product product = productRepository.findById(id).get();
    product.setAvatar(imageUrl);
    productRepository.save(product);
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
