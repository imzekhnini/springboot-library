package ds.dms.library.dao;

import ds.dms.library.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId")
    List<Review> findReviewsByBookId(@Param("bookId") Long bookId);

    @Query("SELECT r FROM Review r WHERE r.student.id = :studentId")
    List<Review> findReviewsByStudentId(@Param("studentId") Long studentId);
}
