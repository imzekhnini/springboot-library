package ds.dms.library.controller;

import ds.dms.library.dto.review.RequestReview;
import ds.dms.library.dto.review.ResponseReview;
import ds.dms.library.services.review.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    public final ReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseReview>> getAllReviews(){
        List<ResponseReview> reviews = reviewService.getAllReview();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReview> getReviewById(@PathVariable Long id){
        ResponseReview review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseReview> addReview(@RequestBody RequestReview requestReview){
        ResponseReview review = reviewService.addReview(requestReview);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseReview> updateReview(@PathVariable Long id, @RequestBody RequestReview requestReview){
        ResponseReview review = reviewService.updateReview(id, requestReview);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteReview(@PathVariable Long id){
        String message = reviewService.deleteReview(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}