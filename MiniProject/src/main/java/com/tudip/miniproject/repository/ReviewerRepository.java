package com.tudip.miniproject.repository;

import com.tudip.miniproject.entity.Reviewer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ReviewerRepository extends PagingAndSortingRepository<Reviewer, Long> {
    Page<Reviewer> findByNameOrContactContaining(String name, String contact, Pageable pageable);
}
