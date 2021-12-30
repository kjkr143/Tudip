package com.tudip.miniproject.service;

import com.tudip.miniproject.entity.Product;
import com.tudip.miniproject.exception.ServiceException;
import com.tudip.miniproject.repository.ProductRepository;
import com.tudip.miniproject.request.ProductRequestDto;
import com.tudip.miniproject.response.ProductResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Page<Product> getAllProducts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> products;
        if (name == null) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findByNameContaining(name, pageable);
        }
        return products;
    }


    public ProductResponseDto addProduct(ProductRequestDto productDto) {
        validate(productDto);
        Product product = changeDTOtOEntity(productDto);
        return changeEntitytoDTO(productRepository.save(product));
    }

    public ProductResponseDto updateProduct(ProductRequestDto productDto, long id) {
        validateForEmpty(productDto);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ServiceException("PRODUCT_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Product productUpdate = product.get();
        if (productDto.getName() != null)
            productUpdate.setName(productDto.getName());
        if (productDto.getDescription() != null)
            productUpdate.setDescription(productDto.getDescription());
        if (!(productUpdate.getPrice() == productDto.getPrice()))
            productUpdate.setPrice(productDto.getPrice());
        return changeEntitytoDTO(productRepository.save(productUpdate));
    }

    public ProductResponseDto getProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ServiceException("PRODUCT_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        return changeEntitytoDTO(product.get());
    }

    private void validate(ProductRequestDto productDto) {

        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            throw new ServiceException("NAME_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            throw new ServiceException("DESCRIPTION_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (productDto.getPrice() < 0) {
            throw new ServiceException("PRICE_CANNOT_BE_NEGATIVE", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateForEmpty(ProductRequestDto productDto) {

        if (productDto.getName().trim().isEmpty()) {
            throw new ServiceException("NAME_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (productDto.getDescription().trim().isEmpty()) {
            throw new ServiceException("DESCRIPTION_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (productDto.getPrice() < 0) {
            throw new ServiceException("PRICE_CANNOT_BE_NEGATIVE", HttpStatus.BAD_REQUEST);
        }
    }

    private ProductResponseDto changeEntitytoDTO(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }

    private Product changeDTOtOEntity(ProductRequestDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

}
