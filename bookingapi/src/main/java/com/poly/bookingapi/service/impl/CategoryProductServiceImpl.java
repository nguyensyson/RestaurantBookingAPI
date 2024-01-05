package com.poly.bookingapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.bookingapi.dto.CategoryAddDTO;
import com.poly.bookingapi.dto.CategoryProductRequest;
import com.poly.bookingapi.dto.ProductViewDTO;
import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.CategoryProductRepository;
import com.poly.bookingapi.service.CategoryProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryProductServiceImpl implements CategoryProductService{

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Override
    public Page<CategoryAddDTO> getAll(CategoryProductRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CategoryProduct> categoryProducts = categoryProductRepository.getAll(pageable);
        List<CategoryAddDTO> viewDTOS = categoryProducts.getContent().stream()
                .map(this::convertToCategoryViewDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(viewDTOS, categoryProducts.getPageable(), categoryProducts.getTotalElements());
    }

    private CategoryAddDTO convertToCategoryViewDTO(CategoryProduct dto) {
        CategoryAddDTO addDTO = new CategoryAddDTO();
        addDTO.setNameCategory(dto.getNameCategory());
        addDTO.setId(dto.getId());
        return addDTO;
    }

    @Override
    public CategoryAddDTO getByID(Integer id) {
        Optional<CategoryProduct> categoryProducts = categoryProductRepository.findById(id);
        CategoryAddDTO dto = new CategoryAddDTO();
            dto.setId(categoryProducts.get().getId());
            dto.setNameCategory(categoryProducts.get().getNameCategory());
//            dto.setListProduct(categoryProducts.get().getListProduct());
        return dto;
    }

    @Override
    public String addCategory(CategoryAddDTO dto) {
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setNameCategory(dto.getNameCategory());
        categoryProduct.setCreateAt(LocalDate.now());
        categoryProduct.setUpdateAt(LocalDate.now());
//        try {
//            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                    "cloud_name", cloudName,
//                    "api_key", apiKey,
//                    "api_secret", apiSecret));
//            // Upload file lên Cloudinary
//            Map uploadResult = cloudinary.uploader().upload(dto.getImage().getBytes(), ObjectUtils.asMap(
//                    "folder", "BeesMeal"
//            ));
//            // Lấy URL của ảnh sau khi upload thành công
//            String imageUrl = (String) uploadResult.get("secure_url");
//            // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
//            categoryProduct.setAvatar(imageUrl);
            categoryProductRepository.save(categoryProduct);
            return "add thành công";
//        } catch (IOException e) {
//            throw new RuntimeException("add thất bại");
//        }
    }

    @Override
    public String updateCategory(CategoryAddDTO dto, Integer id) {
        Optional<CategoryProduct> categoryProduct = categoryProductRepository.findById(id);
        categoryProduct.get().setNameCategory(dto.getNameCategory());
        categoryProduct.get().setUpdateAt(LocalDate.now());
        categoryProductRepository.save(categoryProduct.get());
        return "update thành công";
    }
}
