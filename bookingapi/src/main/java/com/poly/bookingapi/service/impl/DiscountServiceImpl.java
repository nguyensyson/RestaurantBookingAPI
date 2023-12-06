package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DiscountDTO;
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
        Discount discountAdd = discountRepository.save(discount);
        if(discountDTO.getGetListProduct() != null){
            for (ProductDTO list: discountDTO.getGetListProduct()) {
                Product product = productRepository.getById(list.getId());
                product.setDiscount(discountAdd);
                product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
                productRepository.save(product);
            }
        }
        return discountAdd;
//        return null;
    }

    @Override
    public Discount update(DiscountDTO discountDTO,Integer id) {
        Optional<Discount> optional = discountRepository.findById(id);
        optional.get().setDiscountValue(discountDTO.getDiscountValue());
        optional.get().setUpdateAt(LocalDate.now());
        optional.get().setNameDiscount(discountDTO.getNameDiscount());
        optional.get().setIntroduce(discountDTO.getIntroduce());
        Discount discountUpdate = discountRepository.save(optional.get());

        List<Product> productList = new ArrayList<>();
        for (ProductDTO d : discountDTO.getGetListProduct()) {
            int count = 0;
            for (Product p : discountUpdate.getListProduct()) {
                if(d.getId() == p.getId()) {
                    count++;
                }
            }

            if(count != 0) {
                productList.add(productRepository.findById(d.getId()).get());
            }
        }

        for (Product p : productList) {
            p.setDiscount(discountUpdate);
            productRepository.save(productRepository.findById(p.getId()).get());
        }
        return discountUpdate;
//        return  optional.map(discount -> {
//            discount.setNameDiscount(discountDTO.getNameDiscount());
//            discount.setDiscountValue(discountDTO.getDiscountValue());
//            discount.setIntroduce(discountDTO.getIntroduce());
//            if(discountDTO.getGetListProduct() != null){
//                for (ProductDTO list: discountDTO.getGetListProduct()) {
//                    list.setDiscount(discountRepository.save(discount));
//                    Product product = productRepository.getById(list.getId());
//                    product.setDiscount(list.getDiscount());
//                    product.setNewPrice(product.getPrice() - ((product.getDiscount().getDiscountValue() * product.getPrice()))/100);
//                    productRepository.save(product);
//                }
//            }
//            return discountRepository.save(discount);
//        }).orElse(null);
//        return null;
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
