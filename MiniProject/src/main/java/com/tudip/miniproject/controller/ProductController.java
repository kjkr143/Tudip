package com.tudip.miniproject.controller;


import com.tudip.miniproject.entity.Product;
import com.tudip.miniproject.request.ProductRequestDto;
import com.tudip.miniproject.response.GetProductsResponse;
import com.tudip.miniproject.response.ProductResponseDto;
import com.tudip.miniproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/product")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GetProductsResponse getProducts(@RequestParam(required = false) String name,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getAllProducts(name, page, size);
        return GetProductsResponse.builder()
                .products(products.getContent())
                .currentPage(products.getNumber())
                .totalPages(products.getTotalPages())
                .totalItems(products.getTotalElements())
                .build();
    }


    @GetMapping(path = "/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProductResponseDto getProduct(@PathVariable long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping(path = "/product")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping(path = "/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProductResponseDto updateProduct(@RequestBody ProductRequestDto productDto, @PathVariable long productId) {
        return productService.updateProduct(productDto, productId);
    }

}
