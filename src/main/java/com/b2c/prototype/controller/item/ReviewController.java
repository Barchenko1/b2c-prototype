package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.processor.item.IReviewProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final IReviewProcessor reviewProcessor;

    public ReviewController(IReviewProcessor reviewProcessor) {
        this.reviewProcessor = reviewProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateReview(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final ReviewDto reviewDto) {
        reviewProcessor.saveUpdateReview(requestParams, reviewDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateReviewStatus(@RequestParam final Map<String, String> requestParams) {
        reviewProcessor.changeReviewStatus(requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReview(@RequestParam final Map<String, String> requestParams) {
        reviewProcessor.deleteReview(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseReviewDto> getReviewList(@RequestParam final Map<String, String> requestParams) {
        return reviewProcessor.getReviewList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseReviewDto getReview(@RequestParam final Map<String, String> requestParams) {
        return reviewProcessor.getReview(requestParams);
    }

    @PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUpdateComment(@RequestParam final Map<String, String> requestParams,
                                                 @RequestBody final ReviewCommentDto reviewCommentDto) {
        reviewProcessor.addUpdateComment(requestParams, reviewCommentDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteComment(@RequestParam final Map<String, String> requestParams) {
        reviewProcessor.deleteComment(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseReviewCommentDto> getCommentList(@RequestParam final Map<String, String> requestParams) {
        return reviewProcessor.getCommentList(requestParams);
    }

}
