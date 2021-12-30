package com.tudip.miniproject.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewerResponseDto {
    private long id;
    private boolean status;
    private String name;
    private String address;
    private String contact;
}
