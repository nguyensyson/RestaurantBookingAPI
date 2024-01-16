package com.poly.bookingapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.bookingapi.config.ImageUploader;
import com.poly.bookingapi.constant.ProductConst;
import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.proxydto.ProductProxy;
import com.poly.bookingapi.repository.*;
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
    @Autowired
    ProductStatusRepository statusRepository;
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
    public Page<ProductViewDTO> getAllNotCombo(ProductSearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Product> productList = productRepository.getAllNotCombo(pageable);

        List<ProductViewDTO> viewDTOS = productList.getContent().stream()
            .map(this::convertToProductViewDTO)
            .collect(Collectors.toList());

//        List<ProductViewDTO> viewDTOS = new ArrayList<>();
//        for (Product p: productList.getContent()
//             ) {
//            ProductViewDTO dto = this.convertToProductViewDTO(p);
//            viewDTOS.add(dto);
//        }

        return new PageImpl<>(viewDTOS, productList.getPageable(), productList.getTotalElements());
    }

    private ProductViewDTO convertToProductViewDTO(Product p) {
        ProductViewDTO dto = new ProductViewDTO();
        dto.setAvatar(p.getAvatar());
        dto.setCategory(p.getCategory());
        if (p.getDiscount() != null) {
            dto.setDiscount(p.getDiscount().getDiscountValue());
        }
        dto.setId(p.getId());
//        dto.setImages(p.getListImage());
        dto.setIntroduce(p.getIntroduce());
        dto.setName(p.getNameProduct());
        dto.setPrice(p.getPrice());
        if (p.getStatus() != null) {
            dto.setStatus(p.getStatus().getId());
        }
        return dto;
    }

    @Override
    public ProductViewDTO getById(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        ProductViewDTO dto = new ProductViewDTO();
        dto.setAvatar(p.get().getAvatar());
        dto.setCategory(p.get().getCategory());
        if (p.get().getDiscount() != null) {
            dto.setDiscount(p.get().getDiscount().getDiscountValue());
        }
        dto.setId(p.get().getId());
//        dto.setImages(p.get().getListImage());
        dto.setIntroduce(p.get().getIntroduce());
        dto.setName(p.get().getNameProduct());
        dto.setPrice(p.get().getPrice());
        if (p.get().getStatus() != null) {
            dto.setStatus(p.get().getStatus().getId());
        }
        return dto;
    }

    private ComboViewDTO convertToComboViewDTO(Product p) {
        ComboViewDTO dto = new ComboViewDTO();
        dto.setAvatar(p.getAvatar());
        dto.setId(p.getId());
        dto.setIntroduce(p.getIntroduce());
        dto.setName(p.getNameProduct());
        dto.setPrice(p.getPrice());
        dto.setListItem(productRepository.getProductByCombo(p.getId()));
        dto.setStatus(p.getStatus().getId());
        return dto;
    }


    @Override
    public Page<ComboViewDTO> searchCombo(ProductComboSearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Product> productList = productRepository.searchComboByName(model.getName(), pageable);
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
        dto.setId(p.get().getId());
        dto.setIntroduce(p.get().getIntroduce());
        dto.setName(p.get().getNameProduct());
        dto.setPrice(p.get().getPrice());
        List<ProductProxy> list = productRepository.getProductByCombo(id);
        for (int i = list.size() - 1; i >= 0; i--) {
            if(productRepository.findById(list.get(i).getId()).get().getCategory().getId() == 1) {
                list.remove(i);
            }
        }
        dto.setListItem(list);
        return dto;
    }

    @Override
    public String addProduct(ProductAddDTO dto) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(dto.getCategory()).get();
        Product product = new Product();
        product.setCategory(categoryProduct);
        product.setNameProduct(dto.getName());
        product.setIntroduce(dto.getIntroduce());
        product.setPrice(dto.getPrice());
        product.setCreatedAt(LocalDate.now());
        product.setUpdateAt(LocalDate.now());
        if(statusRepository.findById(1).get() != null) {
            product.setStatus(statusRepository.findById(1).get());
        }
        Product productAdd = productRepository.save(product);
        try {
            byte[] imageData = dto.getImages().getBytes();
            Thread thread = new Thread(new ImageUploader(imageData, productAdd.getId(), productRepository));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Add thành công";
    }

    @Override
    public String updateProduct(ProductAddDTO dto, Integer id) {
        Optional<Product> product = productRepository.findById(id);
        //  product.get().setCategory(dto.getCategory());
        product.get().setNameProduct(dto.getName());
        product.get().setIntroduce(dto.getIntroduce());
        product.get().setPrice(dto.getPrice());
        product.get().setStatus(statusRepository.findById(dto.getStatus()).get());
        product.get().setUpdateAt(LocalDate.now());
        Product productAdd = productRepository.save(product.get());
        try {
            byte[] imageData = dto.getImages().getBytes();
            Thread thread = new Thread(new ImageUploader(imageData, productAdd.getId(), productRepository));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            ImageProduct imageProduct = new ImageProduct();
//            imageProduct.setProduct(productAdd);
//            imageProduct.setImages(imageUrl);
//            imageProduct.setCreatedAt(LocalDate.now());
//            imageProduct.setUpdateAt(LocalDate.now());
//            ImageProduct imageProduct1 = imageProductRepository.save(imageProduct);

        return "update thành công";
    }

    @Override
    public ProductDTO addCombo(ComboAddDTO dto) {
            Product combo = new Product();
            combo.setCategory(categoryProductRepository.findById(1).orElseThrow());
            combo.setNameProduct(dto.getName());
            combo.setIntroduce(dto.getIntroduce());
            combo.setCreatedAt(LocalDate.now());
            combo.setUpdateAt(LocalDate.now());
        if(statusRepository.findById(1).get() != null) {
            combo.setStatus(statusRepository.findById(1).get());
        }
            Product comboAdd = productRepository.save(combo);
        if(dto.getAvatar().isPresent()) {
            try {
                byte[] imageData = dto.getAvatar().orElse(null).getBytes();
                Thread thread = new Thread(new ImageUploader(imageData, comboAdd.getId(), productRepository));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ProductDTO(comboAdd.getId(), comboAdd.getNameProduct(), comboAdd.getPrice(), 0);
    }

    @Override
    public String updateCombo(ComboAddDTO dto, Integer id) {
            Optional<Product> combo = productRepository.findById(id);
            combo.get().setUpdateAt(LocalDate.now());
            combo.get().setNameProduct(dto.getName());
            combo.get().setIntroduce(dto.getIntroduce());
            Product comboUpdate = productRepository.save(combo.get());
            if(dto.getAvatar().isPresent()) {
                try {
                    byte[] imageData = dto.getAvatar().orElse(null).getBytes();
                    Thread thread = new Thread(new ImageUploader(imageData, comboUpdate.getId(), productRepository));
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return "update thành công";
    }

    @Override
    public String changeProduct(ChangeProductDTO dto, Integer id){
        Product combo = productRepository.findById(id).orElseThrow();
        if(dto.getListPorduct() == null) return null;
        List<ProductDTO> list = dto.getListPorduct();
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getQuantity() <= 0) {
                if (detailRepository.findByComboAndProduct(id, list.get(i).getId()) != null){
                    ComboDetail detail = detailRepository.findByComboAndProduct(id, list.get(i).getId());
                    detailRepository.delete(detail);
                }
                list.remove(i);
            }
        }

        long price = 0;
        for (ProductDTO p : list) {
            if (detailRepository.findByComboAndProduct(id, p.getId()) != null) {
                ComboDetail detail = detailRepository.findByComboAndProduct(id, p.getId());
                detail.setQuantity(p.getQuantity());
                detailRepository.save(detail);
            } else {
                Product product = productRepository.getById(p.getId());
                ComboDetail detail = new ComboDetail();
                detail.setCombo(combo);
                detail.setItem(product);
                detail.setNameProduct(p.getNameProduct());
                detail.setPrice(p.getPrice());
                detail.setQuantity(p.getQuantity());
                detail.setCreatedAt(LocalDate.now());
                detail.setUpdateAt(LocalDate.now());
                detailRepository.save(detail);
            }

            Product product = productRepository.getById(p.getId());
            price = price + (product.getPrice()*p.getQuantity());
        }
        combo.setPrice(price);
        productRepository.save(combo);
        return "thành công";
    };

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

    @Override
    public List<ProductProxy> getAll(Integer id) {
        return productRepository.getAll(id);
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
            if(product.getDiscount() != null) {
                productCard.setDiscount(product.getDiscount().getDiscountValue());
            }
            productCardList.add(productCard);
        }
        return productCardList;
    }

    @Override
    public List<ProductProxy> getProductNotCOmbo(Integer id){
        List<ProductProxy> list = productRepository.getProductByCombo(id);
        for (int i = list.size() - 1; i >= 0; i--) {
            if(productRepository.findById(list.get(i).getId()).get().getCategory().getId() == 1) {
                list.remove(i);
            }
        }
        return list;
    };

}
