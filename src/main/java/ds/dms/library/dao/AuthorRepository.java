package ds.dms.library.dao;

import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query(value = "SELECT a.* FROM author a JOIN book b ON b.author_id = a.id GROUP BY a.id ORDER BY COUNT(b.id)  DESC LIMIT 1", nativeQuery = true)
    Author findTopAuthorWithMostBooks();
}
