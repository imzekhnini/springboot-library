package ds.dms.library.dao;

import ds.dms.library.entities.Borrower;
import ds.dms.library.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Long> {
    @Query("SELECT br.id, s.name, b.title, br.borrowedDate FROM Borrower br JOIN br.book b JOIN br.student s WHERE br.returnDate IS NULL ORDER BY br.id")
    List<Object[]> findOverdueBorrowedBooks();

    @Query("SELECT COUNT(br) FROM Borrower br WHERE br.student.id = :studentId")
    Object[] findCountTotalBooksByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT b.title, br.borrowedDate, br.returnDate FROM Borrower br JOIN br.book b JOIN br.student s WHERE s.id = :studentId")
    List<Object[]> findBorrowingHistoryByStudentId(@Param("studentId") Long studentId);

    @Query(value = "SELECT br.student_id, COUNT(br.id) AS borrow_count " +
            "FROM borrower br " +
            "GROUP BY br.student_id " +
            "ORDER BY borrow_count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTopBorrowers();

    @Query("SELECT br.student FROM Borrower br WHERE br.book.id = :bookId")
    List<Student> findAllBorrowersOfBookById(@Param("bookId") Long bookId);

    @Query("SELECT br FROM Borrower br WHERE br.book.id = :bookId")
    List<Borrower> findAllBorrowersWithDetailsOfBookById(@Param("bookId") Long bookId);
}
