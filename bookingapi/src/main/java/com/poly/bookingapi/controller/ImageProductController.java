package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ImageProductDTO;
import com.poly.bookingapi.fileupload.FileUpLoad;
import com.poly.bookingapi.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageProductController {
    @Autowired
    private ImageProductService imageProductService;

    @Autowired
    private FileUpLoad fileUpLoad;

    @GetMapping("/view")
    public ResponseEntity<List<ImageProductDTO>> getAllProduct(@RequestParam Optional<Integer> pageNo) {
        try {
            List<ImageProductDTO> list =  imageProductService.getListImageProduct(pageNo.orElse(0));
            System.out.println(123);
            return new ResponseEntity<List<ImageProductDTO>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<ImageProductDTO>>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<LinkedHashMap<String, Object>> add(@RequestParam("images")
                                                             MultipartFile images, @RequestBody ImageProductDTO imageProductDTO) throws IOException {

        String url = fileUpLoad.uploadFile(images);
        imageProductService.add(imageProductDTO,url);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
