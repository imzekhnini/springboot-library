package ds.dms.library.dao;

import ds.dms.library.entities.Borrowor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorroworRepository extends JpaRepository<Borrowor,Long> {
}
