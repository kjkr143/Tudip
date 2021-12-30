package com.tudip.miniproject.response;

import com.tudip.miniproject.entity.Product;
import com.tudip.miniproject.entity.Reviewer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponseDto {
    private double rating;
    private boolean ratingStatus;
    private Product product;
    private Reviewer reviewer;
}
