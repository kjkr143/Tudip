package com.tudip.miniproject.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewerRequestDto {
    private Long id;
    private boolean status = true;
    private String name;
    private String address;
    private String contact;
}
