package ds.dms.library.services.review;

import ds.dms.library.dto.review.RequestReview;
import ds.dms.library.dto.review.ResponseReview;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    List<ResponseReview> getAllReview();
    ResponseReview getReviewById(Long id);
    ResponseReview addReview(RequestReview requestReview);
    ResponseReview updateReview(Long id, RequestReview requestReview);
    String deleteReview(Long id);

    List<ResponseReview> getReviewByBookId(Long id);
    List<ResponseReview> getReviewsByStudentId(Long id);

    Page<ResponseReview> getReviewsPaginated(Integer page, Integer size, String sortField, String sortDirection);
}
