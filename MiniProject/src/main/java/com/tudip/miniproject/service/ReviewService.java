package com.tudip.miniproject.service;


import com.tudip.miniproject.entity.Product;
import com.tudip.miniproject.entity.Review;
import com.tudip.miniproject.entity.Reviewer;
import com.tudip.miniproject.exception.ServiceException;
import com.tudip.miniproject.repository.ProductRepository;
import com.tudip.miniproject.repository.ReviewRepository;
import com.tudip.miniproject.repository.ReviewerRepository;
import com.tudip.miniproject.request.RatingRequestDto;
import com.tudip.miniproject.request.ReviewRequestDto;
import com.tudip.miniproject.response.RatingResponseDto;
import com.tudip.miniproject.response.ReviewResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ReviewResponseDto addReview(ReviewRequestDto reviewDto) {
        validateReview(reviewDto);
        Optional<Product> product = productRepository.findById(reviewDto.getProductId());
        if (!product.isPresent()) {
            throw new ServiceException("PRODUCT_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Optional<Reviewer> reviewer = reviewerRepository.findById(reviewDto.getReviewerId());
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Review review = getReview();
        review.setReview(reviewDto.getReview());
        review.setReviewStatus(reviewDto.isReviewStatus());
        review.setProduct(product.get());
        review.setReviewer(reviewer.get());
        review = reviewRepository.save(review);
        return changeEntitytoReviewDTO(review);
    }

    public RatingResponseDto addRating(RatingRequestDto ratingDto) {
        validateRating(ratingDto);
        Optional<Product> product = productRepository.findById(ratingDto.getProductId());
        if (!product.isPresent()) {
            throw new ServiceException("PRODUCT_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Optional<Reviewer> reviewer = reviewerRepository.findById(ratingDto.getReviewerId());
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Review review = getReview();
        review.setRating(ratingDto.getRating());
        review.setRatingStatus(ratingDto.isRatingStatus());
        review.setProduct(product.get());
        review.setReviewer(reviewer.get());
        review = reviewRepository.save(review);
        return changeEntitytoRatingDTO(review);
    }

    public ReviewResponseDto getReview(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (!review.isPresent()) {
            throw new ServiceException("REVIEW_ID_IS_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        return changeEntitytoReviewDTO(review.get());
    }

    public ReviewResponseDto updateReview(ReviewRequestDto reviewDto, long reviewId) {
        validateReview(reviewDto);
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (!review.isPresent()) {
            throw new ServiceException("REVIEW_ID_IS_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Review reviewUpdate = review.get();
        reviewUpdate.setReview(reviewDto.getReview());
        reviewUpdate.setReviewStatus(reviewDto.isReviewStatus());
        reviewUpdate = reviewRepository.save(reviewUpdate);
        return changeEntitytoReviewDTO(reviewUpdate);
    }

    public RatingResponseDto updateRating(RatingRequestDto ratingDto, long id) {
        validateRating(ratingDto);
        Optional<Review> review = reviewRepository.findById(id);
        if (!review.isPresent()) {
            throw new ServiceException("REVIEW_ID_IS_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Review reviewUpdate = review.get();
        reviewUpdate.setRating(ratingDto.getRating());
        reviewUpdate.setRatingStatus(ratingDto.isRatingStatus());
        reviewUpdate = reviewRepository.save(reviewUpdate);
        return changeEntitytoRatingDTO(reviewUpdate);
    }

    private void validateReview(ReviewRequestDto reviewDto) {
        if (reviewDto.getReview() == null || reviewDto.getReview().trim().isEmpty()) {
            throw new ServiceException("REVIEW_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRating(RatingRequestDto ratingDto) {
        if (ratingDto.getRating() < 0) {
            throw new ServiceException("RATING_CANNOT_BE_NEGATIVE", HttpStatus.BAD_REQUEST);
        }
    }

    private ReviewResponseDto changeEntitytoReviewDTO(Review review) {
        return modelMapper.map(review, ReviewResponseDto.class);
    }

    private RatingResponseDto changeEntitytoRatingDTO(Review review) {
        return modelMapper.map(review, RatingResponseDto.class);
    }

    private Review getReview() {
        Review review = new Review();
        review.setReviewStatus(true);
        review.setRatingStatus(true);
        return review;
    }

}
