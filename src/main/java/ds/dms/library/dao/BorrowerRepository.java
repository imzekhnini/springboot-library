package ds.dms.library.dao;

import ds.dms.library.entities.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Long> {
    @Query(value = "SELECT br.student_id, COUNT(br.id) AS borrow_count " +
            "FROM borrower br " +
            "GROUP BY br.student_id " +
            "ORDER BY borrow_count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTopBorrowers();
}
