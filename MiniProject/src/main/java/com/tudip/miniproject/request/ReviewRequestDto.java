package com.tudip.miniproject.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private String review;
    private boolean reviewStatus;
    private long productId;
    private long reviewerId;
}
