package com.poly.bookingapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.bookingapi.constant.ProductConst;
import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.ComboDetail;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.CategoryProductRepository;
import com.poly.bookingapi.repository.ComboDetailRepository;
import com.poly.bookingapi.repository.ImageProductRepository;
import com.poly.bookingapi.repository.ProductRepository;
import com.poly.bookingapi.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryProductRepository categoryProductRepository;
    @Autowired
    ImageProductRepository imageProductRepository;
    @Autowired
    ComboDetailRepository detailRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

//    @Override
//    public Page<ProductViewDTO> getAllNotCombo() {
//        Page<Product> productList = productRepository.getAllNotCombo();
//        Page<ProductViewDTO> viewDTOS = new P
//        for (Product p : productList) {
//            ProductViewDTO dto = new ProductViewDTO();
//            dto.setAvatar(p.getAvatar());
//            dto.setCategory(p.getCategory());
//            dto.setDiscount(p.getDiscount().getDiscountValue());
//            dto.setId(p.getId());
//            dto.setImages(p.getListImage());
//            dto.setIntroduce(p.getIntroduce());
//            dto.setName(p.getNameProduct());
//            dto.setPrice(p.getPrice());
//            dto.setStatus(p.getStatus().getId());
//            viewDTOS.add(dto);
//        }
//        return viewDTOS;
//    }

    @Override
    public Page<ProductViewDTO> getAllNotCombo() {
        Page<Product> productList = productRepository.getAllNotCombo();

        List<ProductViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToProductViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    private ProductViewDTO convertToProductViewDTO(Product p) {
        ProductViewDTO dto = new ProductViewDTO();
        dto.setAvatar(p.getAvatar());
        dto.setCategory(p.getCategory());
        dto.setDiscount(p.getDiscount().getDiscountValue());
        dto.setId(p.getId());
        dto.setImages(p.getListImage());
        dto.setIntroduce(p.getIntroduce());
        dto.setName(p.getNameProduct());
        dto.setPrice(p.getPrice());
        dto.setStatus(p.getStatus().getId());
        return dto;
    }


    @Override
    public Page<ProductViewDTO> getByCategory(Integer id) {
        Page<Product> productList = productRepository.getByCategory(id);
        List<ProductViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToProductViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    @Override
    public Page<ProductViewDTO> search(String name) {
        Page<Product> productList = productRepository.searchByName(name);
        List<ProductViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToProductViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    @Override
    public ProductViewDTO getById(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        ProductViewDTO dto = new ProductViewDTO();
        dto.setAvatar(p.get().getAvatar());
        dto.setCategory(p.get().getCategory());
        dto.setDiscount(p.get().getDiscount().getDiscountValue());
        dto.setId(p.get().getId());
        dto.setImages(p.get().getListImage());
        dto.setIntroduce(p.get().getIntroduce());
        dto.setName(p.get().getNameProduct());
        dto.setPrice(p.get().getPrice());
        dto.setStatus(p.get().getStatus().getId());
        return dto;
    }

    @Override
    public Page<ComboViewDTO> getAllCombo() {
        Page<Product> productList = productRepository.getAllCombo();

        List<ComboViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToComboViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    private ComboViewDTO convertToComboViewDTO(Product p) {
        ComboViewDTO dto = new ComboViewDTO();
        dto.setAvatar(p.getAvatar());
        dto.setCategory(p.getCategory());
        dto.setDiscount(p.getDiscount().getDiscountValue());
        dto.setId(p.getId());
        dto.setImages(p.getListImage());
        dto.setIntroduce(p.getIntroduce());
        dto.setName(p.getNameProduct());
        dto.setPrice(p.getPrice());
        dto.setStatus(p.getStatus().getId());
        dto.setListItem(p.getListitem());
        return dto;
    }


    @Override
    public Page<ComboViewDTO> searchCombo(String name) {
        Page<Product> productList = productRepository.searchComboByName(name);
        List<ComboViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToComboViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    @Override
    public ComboViewDTO getComboById(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        ComboViewDTO dto = new ComboViewDTO();
        dto.setAvatar(p.get().getAvatar());
        dto.setCategory(p.get().getCategory());
        dto.setDiscount(p.get().getDiscount().getDiscountValue());
        dto.setId(p.get().getId());
        dto.setImages(p.get().getListImage());
        dto.setIntroduce(p.get().getIntroduce());
        dto.setName(p.get().getNameProduct());
        dto.setPrice(p.get().getPrice());
        dto.setStatus(p.get().getStatus().getId());
        dto.setListItem(p.get().getListitem());
        return dto;
    }

    @Override
    public String addProduct(ProductAddDTO dto) {
        Product product = new Product();
        product.setCategory(dto.getCategory());
        product.setNameProduct(dto.getName());
        product.setIntroduce(dto.getIntroduce());
        product.setPrice(dto.getPrice());
        product.setCreatedAt(LocalDate.now());
        product.setUpdateAt(LocalDate.now());
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));

            // Upload file lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(dto.getImages().getBytes(), ObjectUtils.asMap(
                "folder", "BeesMeal"
            ));

            // Lấy URL của ảnh sau khi upload thành công
            String imageUrl = (String) uploadResult.get("secure_url");

            // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
            product.setAvatar(imageUrl);

            Product productAdd = productRepository.save(product);
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setProduct(productAdd);
            imageProduct.setImages(imageUrl);
            imageProduct.setCreatedAt(LocalDate.now());
            imageProduct.setUpdateAt(LocalDate.now());
            ImageProduct imageProduct1 = imageProductRepository.save(imageProduct);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "add thành công";
    }

    @Override
    public String updateProduct(ProductAddDTO dto, Integer id) {
        Optional<Product> product = productRepository.findById(id);
        product.get().setCategory(dto.getCategory());
        product.get().setNameProduct(dto.getName());
        product.get().setIntroduce(dto.getIntroduce());
        product.get().setPrice(dto.getPrice());
        product.get().setUpdateAt(LocalDate.now());
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));

            // Upload file lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(dto.getImages().getBytes(), ObjectUtils.asMap(
                "folder", "BeesMeal"
            ));

            // Lấy URL của ảnh sau khi upload thành công
            String imageUrl = (String) uploadResult.get("secure_url");

            // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
            product.get().setAvatar(imageUrl);

            Product productAdd = productRepository.save(product.get());
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setProduct(productAdd);
            imageProduct.setImages(imageUrl);
            imageProduct.setCreatedAt(LocalDate.now());
            imageProduct.setUpdateAt(LocalDate.now());
            ImageProduct imageProduct1 = imageProductRepository.save(imageProduct);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "update thành công";
    }

    @Override
    public String addCombo(ComboAddDTO dto) {
        try {
            Product combo = new Product();
            combo.setCategory(categoryProductRepository.findById(1).get());
            combo.setNameProduct(dto.getName());
            combo.setIntroduce(dto.getIntroduce());

            Long sum = Long.parseLong("0");
            for (Product p : dto.getListItem()) {
                sum = sum + p.getPrice();
            }
            combo.setPrice(sum);
            combo.setCreatedAt(LocalDate.now());
            combo.setUpdateAt(LocalDate.now());

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
            // Upload file lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(dto.getAvatar().getBytes(), ObjectUtils.asMap(
                "folder", "BeesMeal"
            ));
            // Lấy URL của ảnh sau khi upload thành công
            String imageUrl = (String) uploadResult.get("secure_url");
            // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
            combo.setAvatar(imageUrl);

            Product comboAdd = productRepository.save(combo);

            for (Product p : dto.getListItem()) {
                ComboDetail detail = new ComboDetail();
                detail.setCombo(comboAdd);
                detail.setItem(p);
                detail.setNameProduct(p.getNameProduct());
                detail.setPrice(p.getPrice());
                detail.setCreatedAt(LocalDate.now());
                detail.setUpdateAt(LocalDate.now());
                ComboDetail detail1 = detailRepository.save(detail);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "add thành công";
    }

    @Override
    public String updateCombo(ComboAddDTO dto, Integer id) {
        try {

            Optional<Product> combo = productRepository.findById(id);
            long sum = 0;
            for (Product p : dto.getListItem()) {
                sum = sum + p.getPrice();
            }
            combo.get().setPrice(sum);
            combo.get().setUpdateAt(LocalDate.now());
            combo.get().setNameProduct(dto.getName());
            combo.get().setIntroduce(dto.getIntroduce());

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
            // Upload file lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(dto.getAvatar().getBytes(), ObjectUtils.asMap(
                "folder", "BeesMeal"
            ));
            // Lấy URL của ảnh sau khi upload thành công
            String imageUrl = (String) uploadResult.get("secure_url");
            // Trả về URL của ảnh đã upload để sử dụng trong frontend hoặc để lưu vào cơ sở dữ liệu
            combo.get().setAvatar(imageUrl);

            Product comboUpdate = productRepository.save(combo.get());

            List<Product> list = new ArrayList<>();
            for (Product p : dto.getListItem()) {

                int count = 0;
                for (ComboDetail i : combo.get().getListitem()) {
                    if (p.getId() == i.getItem().getId()) {
                        count++;
                    }
                }

                if (count != 0) {
                    list.add(p);
                }
            }

            for (Product p : list) {
                ComboDetail detail = new ComboDetail();
                detail.setCombo(comboUpdate);
                detail.setItem(p);
                detail.setNameProduct(p.getNameProduct());
                detail.setPrice(p.getPrice());
                detail.setCreatedAt(LocalDate.now());
                detail.setUpdateAt(LocalDate.now());
                ComboDetail detail1 = detailRepository.save(detail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "update thành công";
    }

    @Override
    public Page<ProductCard> findBySearch(ProductSearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        String sql = this.buildQuery(model);
        Query query = entityManager.createQuery(sql, ProductCard.class);
        this.setParameter(query, model);
        query.setFirstResult(model.getPage() * model.getSize());
        query.setMaxResults(model.getSize());
        List<Product> list = query.getResultList();
        List<ProductCard> productCardList = this.fromProductList(list);
        String countSql = this.buildCountQuery(model);
        Query countQuery = entityManager.createQuery(countSql);
        this.setParameter(countQuery, model);
        Integer count = (Integer) countQuery.getSingleResult();
        return new PageImpl<>(productCardList, pageable, count);
    }

    private String buildQuery(ProductSearchRequest model) {
        StringBuilder sql = new StringBuilder();
        sql.append("select p from com.poly.bookingapi.entity.Product p ");
        sql.append("where 1=1 ");
        if (model.getKeyword() != null && !model.getKeyword().isEmpty()) {
            sql.append("and (p.nameProduct like :keyword or p.introduce like :keyword) ");
        }
        if (model.getCategoryIdList() != null && !model.getCategoryIdList().isEmpty()) {
            sql.append("and p.category.id in :categoryIdList ");
        }
        if (model.getSortBy() == null) {
            model.setSortBy(ProductConst.SORT_BY_DEFAULT);
        }
        if (ProductConst.SORT_BY_DEFAULT.equals(model.getSortBy())) {
            sql.append("order by p.id desc ");
        } else if (ProductConst.SORT_BY_PRICE_ASC.equals(model.getSortBy())) {
            sql.append("order by p.price asc ");
        } else if (ProductConst.SORT_BY_PRICE_DESC.equals(model.getSortBy())) {
            sql.append("order by p.price desc ");
        }
        return sql.toString();
    }

    private String buildCountQuery(ProductSearchRequest model) {
        StringBuilder sql = new StringBuilder();
        sql.append("select cast(count(p.id) as int) from com.poly.bookingapi.entity.Product p ");
        sql.append("where 1=1 ");
        if (model.getKeyword() != null && !model.getKeyword().isEmpty()) {
            sql.append("and (p.nameProduct like :keyword or p.introduce like :keyword) ");
        }
        if (model.getCategoryIdList() != null && !model.getCategoryIdList().isEmpty()) {
            sql.append("and p.category.id in :categoryIdList ");
        }
        return sql.toString();
    }

    private void setParameter(Query query, ProductSearchRequest model) {
        if (model.getKeyword() != null && !model.getKeyword().isEmpty()) {
            query.setParameter("keyword", "%" + model.getKeyword() + "%");
        }
        if (model.getCategoryIdList() != null && !model.getCategoryIdList().isEmpty()) {
            query.setParameter("categoryIdList", model.getCategoryIdList());
        }
    }

    private List<ProductCard> fromProductList(List<Product> productList) {
        List<ProductCard> productCardList = new ArrayList<>();
        for (Product product : productList) {
            ProductCard productCard = new ProductCard();
            productCard.setId(product.getId());
            productCard.setName(product.getNameProduct());
            productCard.setPrice(product.getPrice());
            productCard.setImageThumbnail(product.getAvatar());
            productCard.setDiscount(product.getDiscount().getDiscountValue());
            productCardList.add(productCard);
        }
        return productCardList;
    }
}
