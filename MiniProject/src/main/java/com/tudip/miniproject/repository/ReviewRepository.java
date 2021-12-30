package com.tudip.miniproject.repository;

import com.tudip.miniproject.entity.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

}
