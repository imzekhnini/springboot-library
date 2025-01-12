package ds.dms.library.services.student;

import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;

import java.util.List;

public interface StudentService {
    List<ResponseStudent> getAllStudent();
    ResponseStudent getStudentById(Long id);
    ResponseStudent addStudent(RequestStudent requestStudent);
    ResponseStudent updateStudent(Long id, RequestStudent requestStudent);
    String deleteStudent(Long id);

    List<ResponseStudent> getBorrowersByBookId(Long id);
}
