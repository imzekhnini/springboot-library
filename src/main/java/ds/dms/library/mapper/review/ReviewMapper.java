package ds.dms.library.mapper.review;

import ds.dms.library.dao.BookRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.review.RequestReview;
import ds.dms.library.dto.review.ResponseReview;
import ds.dms.library.entities.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BookRepository.class, StudentRepository.class})
public interface ReviewMapper {
    Review toEntity(RequestReview requestReview);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "student.name", target = "studentName")
    ResponseReview toReviewResponse(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "book", expression = "java(requestReview.getBookId() != null ? bookRepository.findById(requestReview.getBookId()).orElse(null) : review.getBook())")
    @Mapping(target = "student", expression = "java(requestReview.getBookId() != null ? studentRepository.findById(requestReview.getBookId()).orElse(null) : review.getStudent())")
    void updateEntityFromRequest(RequestReview requestReview, @MappingTarget Review review, @Context BookRepository bookRepository, @Context StudentRepository studentRepository);
}
