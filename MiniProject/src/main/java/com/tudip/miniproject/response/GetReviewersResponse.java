package com.tudip.miniproject.response;

import com.tudip.miniproject.entity.Reviewer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class GetReviewersResponse {
    private List<Reviewer> reviewers;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
