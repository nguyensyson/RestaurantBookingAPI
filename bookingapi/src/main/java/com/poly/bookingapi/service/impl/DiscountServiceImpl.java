package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.dto.DiscountViewDTO;
import com.poly.bookingapi.dto.ProductDTO;
import com.poly.bookingapi.entity.Discount;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.DiscountRepository;
import com.poly.bookingapi.repository.ProductRepository;
import com.poly.bookingapi.service.DiscountService;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Discount add(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setNameDiscount(discountDTO.getNameDiscount());
        discount.setDiscountValue(discountDTO.getDiscountValue());
        discount.setIntroduce(discountDTO.getIntroduce());
        discount.setCreateAt(LocalDate.now());
        discount.setStatus(1);
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        Discount discountAdd = discountRepository.save(discount);
        if(discountDTO.getListProduct() != null){
            for (Integer i : discountDTO.getListProduct()) {
                Product product = productRepository.getById(i);
                product.setDiscount(discountAdd);
                product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
                productRepository.save(product);
            }
        }
        return discountAdd;
    }

    @Override
    public Discount update(DiscountDTO discountDTO,Integer id) {
        Optional<Discount> optional = discountRepository.findById(id);
        optional.get().setDiscountValue(discountDTO.getDiscountValue());
        optional.get().setUpdateAt(LocalDate.now());
        optional.get().setNameDiscount(discountDTO.getNameDiscount());
        optional.get().setIntroduce(discountDTO.getIntroduce());
        optional.get().setStartDate(discountDTO.getStartDate());
        optional.get().setEndDate(discountDTO.getEndDate());
        Discount discountUpdate = discountRepository.save(optional.get());

        if(discountDTO.getListProduct() != null){
            for (Integer i : discountDTO.getListProduct()) {
                Product product = productRepository.getById(i);
                product.setDiscount(discountUpdate);
                product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
                productRepository.save(product);
            }
        }
        return discountUpdate;
    }

    @Override
    public Discount delete(Integer id) {
        Optional<Discount> optional = discountRepository.findById(id);
        return optional.map(discount -> {
            for (Product pro: discount.getListProduct()) {
                Product product = productRepository.getById(pro.getId());
                product.setDiscount(null);
                product.setNewPrice(null);
                productRepository.save(product);
            }
            discountRepository.delete(discount);
            return discount;
        }).orElse(null);
    }

    @Override
    public DiscountViewDTO getById(Integer id) {
        Discount discount = discountRepository.getById(id);
        DiscountViewDTO dto = new DiscountViewDTO();
        dto.setIntroduce(discount.getIntroduce());
        dto.setDiscountValue(discount.getDiscountValue());
        dto.setStatus(discount.getStatus());
        dto.setNameDiscount(discount.getNameDiscount());
        dto.setEndDate(discount.getEndDate());
        dto.setStartDate(discount.getStartDate());
        dto.setListProduct(productRepository.getProductByDiscount(id));
        return dto;
    }

    @Override
    public Discount enable(Integer id) {
        Optional<Discount> optional = discountRepository.findById(id);
        return optional.map(discount -> {
            if(discount.getStatus() == 1){
                discount.setStatus(2);
            }else {
                discount.setStatus(1);
            }
            discountRepository.save(discount);
            return discount;
        }).orElse(null);
    }

    @Override
    public List<Discount> getALL(){
        return discountRepository.findAll();
    };

    public void main(String[] args) {

        List<Discount> list = discountRepository.findAll();
        for (Discount i : list
             ) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> checkDiscountStatus(i), 0, 1, TimeUnit.HOURS);
        }

    }

    private void checkDiscountStatus(Discount discount) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(discount.getEndDate())) {
            Discount discount1 = discountRepository.getById(discount.getId());
            discount1.setStatus(0);
            discountRepository.save(discount1);
        }
    }
}
