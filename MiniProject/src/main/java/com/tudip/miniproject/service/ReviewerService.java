package com.tudip.miniproject.service;

import com.tudip.miniproject.entity.Reviewer;
import com.tudip.miniproject.exception.ServiceException;
import com.tudip.miniproject.repository.ReviewerRepository;
import com.tudip.miniproject.request.ReviewerRequestDto;
import com.tudip.miniproject.request.ReviewerStatusRequest;
import com.tudip.miniproject.response.ReviewerResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewerService {
    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Page<Reviewer> getAllReviewers(String name, String contact, int page, int size) {
        Page<Reviewer> reviewers;
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        if ((name == null || name.isEmpty()) && (contact == null || contact.isEmpty())) {
            reviewers = reviewerRepository.findAll(pageable);
        } else {
            reviewers = reviewerRepository.findByNameOrContactContaining(name, contact, pageable);
        }
        return reviewers;
    }

    public ReviewerResponseDto addReviewer(ReviewerRequestDto reviewerDto) {
        validate(reviewerDto);
        Reviewer reviewer = changeDTOtOEntity(reviewerDto);
        reviewer = reviewerRepository.save(reviewer);
        return changeEntitytoDTO(reviewer);
    }

    public ReviewerResponseDto updateReviewer(ReviewerRequestDto reviewerDto, long id) {
        validateForEmpty(reviewerDto);
        Optional<Reviewer> reviewer = reviewerRepository.findById(id);
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Reviewer reviewerUpdate = reviewer.get();
        if (reviewerDto.getName() != null)
            reviewerUpdate.setName(reviewerDto.getName());
        if (reviewerDto.getAddress() != null)
            reviewerUpdate.setAddress(reviewerDto.getAddress());
        if (reviewerDto.getContact() != null)
            reviewerUpdate.setContact(reviewerDto.getContact());
        if (!(reviewerUpdate.isStatus() == reviewerDto.isStatus()))
            reviewerUpdate.setStatus(reviewerDto.isStatus());
        return changeEntitytoDTO(reviewerRepository.save(reviewerUpdate));
    }

    public void deleteReviewer(Long id) {
        Optional<Reviewer> reviewer = reviewerRepository.findById(id);
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        reviewerRepository.deleteById(id);
    }

    public ReviewerResponseDto enableOrDisableReviewer(ReviewerStatusRequest reviewerDto, long reviewerId) {
        Optional<Reviewer> reviewer = reviewerRepository.findById(reviewerId);
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        Reviewer reviewerUpdate = reviewer.get();
        reviewerUpdate.setStatus(reviewerDto.isStatus());
        return changeEntitytoDTO(reviewerRepository.save(reviewerUpdate));
    }


    public ReviewerResponseDto getReviewer(long id) {
        Optional<Reviewer> reviewer = reviewerRepository.findById(id);
        if (!reviewer.isPresent()) {
            throw new ServiceException("REVIEWER_ID_NOT_FOUND_IN_DATABASE", HttpStatus.NOT_FOUND);
        }
        return changeEntitytoDTO(reviewer.get());
    }


    private void validate(ReviewerRequestDto reviewerDto) {

        if (reviewerDto.getName() == null || reviewerDto.getName().isEmpty()) {
            throw new ServiceException("NAME_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (reviewerDto.getContact() == null || reviewerDto.getContact().isEmpty()) {
            throw new ServiceException("CONTACT_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (reviewerDto.getAddress() == null || reviewerDto.getAddress().isEmpty()) {
            throw new ServiceException("ADDRESS_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateForEmpty(ReviewerRequestDto reviewerDto) {
//        if(reviewerDto.getId()==null){
//            throw  new ServiceException("ID_IS_MANDATORY",HttpStatus.BAD_REQUEST);
//        }
        if (reviewerDto.getName().trim().isEmpty()) {
            throw new ServiceException("NAME_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (reviewerDto.getContact().trim().isEmpty()) {
            throw new ServiceException("CONTACT_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
        if (reviewerDto.getAddress().trim().isEmpty()) {
            throw new ServiceException("ADDRESS_IS_MANDATORY", HttpStatus.BAD_REQUEST);
        }
    }

    private ReviewerResponseDto changeEntitytoDTO(Reviewer reviewer) {
        return modelMapper.map(reviewer, ReviewerResponseDto.class);
    }

    private Reviewer changeDTOtOEntity(ReviewerRequestDto reviewerDto) {
        return modelMapper.map(reviewerDto, Reviewer.class);
    }


}
