package com.tudip.miniproject.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDto {
    private double rating;
    private boolean ratingStatus;
    private long productId;
    private long reviewerId;
}
