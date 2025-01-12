package ds.dms.library.dao;

import ds.dms.library.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN s.borrowers br WHERE br.book.id = :bookId")
    List<Student> findBorrowersByBookId(@Param("bookId") Long bookId);
}
