package ds.dms.library.services.student;

import ds.dms.library.dao.BorrowerRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.student.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    public final StudentRepository studentRepository;
    public final StudentMapper studentMapper;
    public final BorrowerRepository borrowerRepository;

    @Override
    public List<ResponseStudent> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        List<ResponseStudent> resStudents = students.stream()
                .map(studentMapper::toResponseStudent)
                .collect(Collectors.toList());
        return resStudents;
    }
    @Override
    public List<ResponseStudent> getBorrowersByBookId(Long id) {
        List<Student> students = studentRepository.findBorrowersByBookId(id);
        List<ResponseStudent> resStudents = students.stream()
                .map(studentMapper::toResponseStudent)
                .collect(Collectors.toList());
        return resStudents;
    }

    @Override
    public Integer getCountTotalBooksByStudentId(Long id) {
        Object[] response = borrowerRepository.findCountTotalBooksByStudentId(id);
        Integer counter = ((Long) response[0]).intValue();
        return counter;
    }

    @Override
    public List<Map<String, Object>> getBorrowingHistoryByStudentId(Long id) {
        List<Object[]> response = borrowerRepository.findBorrowingHistoryByStudentId(id);
        List<Map<String, Object>> books = new ArrayList<>();
        for (Object[] entry : response){
            Map<String, Object> book = new HashMap<>();
            book.put("book_title", entry[0]);
            Timestamp tsBorrowDate = (Timestamp) entry[1];
            OffsetDateTime borrowDate = tsBorrowDate.toInstant().atOffset(ZoneOffset.UTC);
            String formatBorrowDate = borrowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            book.put("borrowed_date", formatBorrowDate);
            if (entry[2] == null){
                book.put("return_date", null);
            }else{
                Timestamp tsReturnDate = (Timestamp) entry[2];
                OffsetDateTime returnDate = tsReturnDate.toInstant().atOffset(ZoneOffset.UTC);
                String formatReturnDate = returnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                book.put("return_date", formatReturnDate);
            }
            books.add(book);
        }
        return books;
    }

    @Override
    public ResponseStudent getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        return studentMapper.toResponseStudent(student);
    }

    @Override
    public ResponseStudent addStudent(RequestStudent requestStudent) {
        Student student = studentMapper.toEntity(requestStudent);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseStudent(savedStudent);
    }

    @Override
    public ResponseStudent updateStudent(Long id, RequestStudent requestStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        studentMapper.updateEntityFromRequest(requestStudent, student);
        Student updatedSaved = studentRepository.save(student);
        return studentMapper.toResponseStudent(updatedSaved);
    }

    @Override
    public String deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        studentRepository.deleteById(id);
        return "Student id: "+ id +" deleted!";
    }


}
