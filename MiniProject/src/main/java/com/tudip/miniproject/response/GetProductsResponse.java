package com.tudip.miniproject.response;

import com.tudip.miniproject.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Builder
public class GetProductsResponse {
    private List<Product> products;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
