package ds.dms.library.dao;

import ds.dms.library.entities.Book;
import ds.dms.library.entities.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByGenre(BookGenre genre);
    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.borrowers br WHERE br.returnDate IS NULL")
    List<Book> findCurrentlyBorrowedBooks();

    @Query("SELECT b FROM Book b JOIN b.borrowers br JOIN br.student WHERE student.id = :studentId")
    List<Book> findBooksBorrowedByStudentById(@Param("studentId") Long studentId);

    @Query("SELECT DISTINCT b.genre, COUNT(b) FROM Book b GROUP BY b.genre")
    List<Object[]> getBookCountByGenre();
}
