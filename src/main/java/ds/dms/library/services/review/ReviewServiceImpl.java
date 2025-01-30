package ds.dms.library.services.review;

import ds.dms.library.dao.BookRepository;
import ds.dms.library.dao.ReviewRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.paginate.PaginatedResponse;
import ds.dms.library.dto.review.RequestReview;
import ds.dms.library.dto.review.ResponseReview;
import ds.dms.library.entities.Book;
import ds.dms.library.entities.Review;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.review.ReviewMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  public final ReviewMapper reviewMapper;
  public final ReviewRepository reviewRepository;
  public final StudentRepository studentRepository;
  public final BookRepository bookRepository;

  public static final Integer MAX_PAGE_SIZE = 10;

  @Override
  public List<ResponseReview> getAllReview() {
    List<Review> reviews = reviewRepository.findAll();
    List<ResponseReview> reviewsRes = reviews.stream()
        .map(reviewMapper::toReviewResponse).collect(Collectors.toList());
    return reviewsRes;
  }

  @Override
  public ResponseReview getReviewById(Long id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    return reviewMapper.toReviewResponse(review);
  }

  @Override
  public List<ResponseReview> getReviewByBookId(Long id) {
    List<Review> reviews = reviewRepository.findReviewsByBookId(id);
    List<ResponseReview> resReviews = reviews.stream()
        .map(reviewMapper::toReviewResponse)
        .collect(Collectors.toList());
    return resReviews;
  }

  @Override
  public List<ResponseReview> getReviewsByStudentId(Long id) {
    List<Review> reviews = reviewRepository.findReviewsByStudentId(id);
    List<ResponseReview> resReviews = reviews.stream()
        .map(reviewMapper::toReviewResponse)
        .collect(Collectors.toList());
    return resReviews;
  }

  @Override
  public PaginatedResponse getReviewsPaginated(Integer page, Integer size, String sortField, String sortDirection) {
    size = size > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : size;
    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Review> reviews = reviewRepository.findAll(pageable);
    PaginatedResponse response = new PaginatedResponse();
    response.setData(reviews.map(reviewMapper::toReviewResponse).getContent());
    response.setCurrentPage(reviews.getNumber());
    response.setTotalItems(reviews.getTotalElements());
    response.setTotalPages(reviews.getTotalPages());
    return response;
  }

  @Override
  public ResponseReview addReview(RequestReview requestReview) {
    Book book = bookRepository.findById(requestReview.getBookId())
        .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + requestReview.getBookId()));
    Student student = studentRepository.findById(requestReview.getStudentId())
        .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + requestReview.getStudentId()));

    Review review = reviewMapper.toEntity(requestReview);
    review.setBook(book);
    review.setStudent(student);

    Review savedReview = reviewRepository.save(review);
    return reviewMapper.toReviewResponse(savedReview);
  }

  @Override
  public ResponseReview updateReview(Long id, RequestReview requestReview) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    reviewMapper.updateEntityFromRequest(requestReview, review, bookRepository, studentRepository);
    Review updatedReview = reviewRepository.save(review);
    return reviewMapper.toReviewResponse(updatedReview);
  }

  @Override
  public String deleteReview(Long id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    reviewRepository.deleteById(id);
    return "Review id: " + id + " deleted!";
  }

}
