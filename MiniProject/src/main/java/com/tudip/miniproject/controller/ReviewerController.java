package com.tudip.miniproject.controller;

import com.tudip.miniproject.entity.Reviewer;
import com.tudip.miniproject.request.ReviewerRequestDto;
import com.tudip.miniproject.request.ReviewerStatusRequest;
import com.tudip.miniproject.response.GetReviewersResponse;
import com.tudip.miniproject.response.ReviewerResponseDto;
import com.tudip.miniproject.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewerController {

	@Autowired
	private ReviewerService reviewerService;
	
	@PostMapping(path = "/reviewer")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ReviewerResponseDto addReviewer(@RequestBody ReviewerRequestDto reviewerDto) {
		return reviewerService.addReviewer(reviewerDto);
	}

	@GetMapping(path = "/reviewer")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GetReviewersResponse getReviewers(@RequestParam(required = false) String name,
											 @RequestParam(required = false) String contact,
											 @RequestParam(defaultValue="0") int page,
											 @RequestParam(defaultValue="10") int size) {
		Page<Reviewer> reviewers = reviewerService.getAllReviewers(name,contact,page, size);
		return GetReviewersResponse.builder()
				.reviewers(reviewers.getContent())
				.currentPage(reviewers.getNumber())
				.totalPages(reviewers.getTotalPages())
				.totalItems(reviewers.getTotalElements())
				.build();
	}

	@GetMapping(path = "/reviewer/{reviewerId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ReviewerResponseDto getReviewer(@PathVariable Long reviewerId) {
			return reviewerService.getReviewer(reviewerId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(path = "/reviewer/{reviewerId}")
	@ResponseBody
	public ReviewerResponseDto updateReviewer(@RequestBody ReviewerRequestDto reviewerDto,@PathVariable Long reviewerId)
	{
			return reviewerService.updateReviewer(reviewerDto, reviewerId);
	}


	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(path = "/reviewer/{reviewerId}")
	@ResponseBody
	public ReviewerResponseDto enableOrDisableReviewer(@RequestBody ReviewerStatusRequest reviewerDto,
													   @PathVariable Long reviewerId) {
			return reviewerService.enableOrDisableReviewer(reviewerDto, reviewerId);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(path = "/reviewer/{reviewerId}")
	public ResponseEntity<String> deleteReviewer(@PathVariable Long reviewerId) {
			reviewerService.deleteReviewer(reviewerId);
			return ResponseEntity.ok("SUCCESSFULLY_DELETED");
	}

}
