package com.tudip.miniproject.controller;

import com.tudip.miniproject.request.RatingRequestDto;
import com.tudip.miniproject.request.ReviewRequestDto;
import com.tudip.miniproject.response.RatingResponseDto;
import com.tudip.miniproject.response.ReviewResponseDto;
import com.tudip.miniproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(path = "/review")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseDto addReview(@RequestBody ReviewRequestDto reviewDto) {
        return reviewService.addReview(reviewDto);
    }

    @GetMapping(path = "/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseDto getReview(@PathVariable long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PutMapping(path = "/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseDto updateReview(@RequestBody ReviewRequestDto reviewDto, @PathVariable long reviewId) {
        return reviewService.updateReview(reviewDto, reviewId);
    }


    @PostMapping(path = "/rating")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RatingResponseDto addRating(@RequestBody RatingRequestDto ratingDto) {
        return reviewService.addRating(ratingDto);
    }

    @PutMapping(path = "/rating/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RatingResponseDto updateRating(@RequestBody RatingRequestDto ratingDto, @PathVariable long id) {
        return reviewService.updateRating(ratingDto, id);
    }

}
