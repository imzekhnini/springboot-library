package ds.dms.library.services.borrower;

import ds.dms.library.dto.borrower.RequestBorrower;
import ds.dms.library.dto.borrower.ResponseBorrower;
import ds.dms.library.dto.student.ResponseStudent;

import java.util.List;

public interface BorrowerService {
    List<ResponseBorrower> getAllBorrowers();
    ResponseBorrower getBorrowerById(Long id);
    ResponseBorrower addBorrower(RequestBorrower requestBorrower);
    ResponseBorrower updateBorrower(Long id, RequestBorrower requestBorrower);
    String deleteBorrower(Long id);

    List<ResponseStudent> getTopBorrowers();
}
