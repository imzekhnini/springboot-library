package ds.dms.library.dao;

import ds.dms.library.entities.Book;
import ds.dms.library.entities.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByGenre(BookGenre genre);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.borrowers br WHERE br.returnDate IS NULL")
    List<Book> findCurrentlyBorrowedBooks();

    @Query("SELECT b FROM Book b JOIN b.borrowers br JOIN br.student WHERE student.id = :studentId")
    List<Book> findBooksBorrowedByStudentById(@Param("studentId") Long studentId);
}
