package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.dto.ProductDTO;
import com.poly.bookingapi.entity.Discount;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.DiscountRepository;
import com.poly.bookingapi.repository.ProductRepository;
import com.poly.bookingapi.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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
        if(discountDTO.getGetListProduct() != null){
            for (ProductDTO list: discountDTO.getGetListProduct()) {
                list.setDiscount(discountRepository.save(discount));
                Product product = productRepository.getById(list.getId());
                product.setDiscount(list.getDiscount());
                product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
                productRepository.save(product);
            }
        }
        return discountRepository.save(discount);
    }

    @Override
    public Discount update(DiscountDTO discountDTO,Integer id) {
        Optional<Discount> optional = discountRepository.findById(id);
        return  optional.map(discount -> {
            discount.setNameDiscount(discountDTO.getNameDiscount());
            discount.setDiscountValue(discountDTO.getDiscountValue());
            discount.setIntroduce(discountDTO.getIntroduce());
            if(discountDTO.getGetListProduct() != null){
                for (ProductDTO list: discountDTO.getGetListProduct()) {
                    list.setDiscount(discountRepository.save(discount));
                    Product product = productRepository.getById(list.getId());
                    product.setDiscount(list.getDiscount());
                    product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
                    productRepository.save(product);
                }
            }
            return discountRepository.save(discount);
        }).orElse(null);
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
    public Discount getById(Integer id) {
        return discountRepository.getById(id);
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
}
