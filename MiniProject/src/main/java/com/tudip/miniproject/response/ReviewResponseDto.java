package com.tudip.miniproject.response;

import com.tudip.miniproject.entity.Product;
import com.tudip.miniproject.entity.Reviewer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private long reviewId;
    private String review;
    private boolean reviewStatus;
    private Product product;
    private Reviewer reviewer;
}
