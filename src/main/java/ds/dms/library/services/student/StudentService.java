package ds.dms.library.services.student;

import ds.dms.library.dto.paginate.PaginatedResponse;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {
  List<ResponseStudent> getAllStudent();

  ResponseStudent getStudentById(Long id);

  ResponseStudent addStudent(RequestStudent requestStudent);

  ResponseStudent updateStudent(Long id, RequestStudent requestStudent);

  String deleteStudent(Long id);

  List<ResponseStudent> getBorrowersByBookId(Long id);

  Integer getCountTotalBooksByStudentId(Long id);

  List<Map<String, Object>> getBorrowingHistoryByStudentId(Long id);

  ResponseEntity<byte[]> generatePdf(Long id);

  String uploadFilePdf(MultipartFile file) throws IOException;

  PaginatedResponse getStudentsPaginated(Integer page, Integer size, String sortField, String sortDirection);
}
