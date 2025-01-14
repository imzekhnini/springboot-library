package ds.dms.library.services.borrower;

import ds.dms.library.dao.BookRepository;
import ds.dms.library.dao.BorrowerRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.borrower.RequestBorrower;
import ds.dms.library.dto.borrower.ResponseBorrower;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.Book;
import ds.dms.library.entities.Borrower;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.borrower.BorrowerMapper;
import ds.dms.library.mapper.student.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class BorrowerServiceImpl implements BorrowerService {
    public final BorrowerRepository borrowerRepository;
    public final BorrowerMapper borrowerMapper;
    public final StudentMapper studentMapper;
    public final BookRepository bookRepository;
    public final StudentRepository studentRepository;

    @Override
    public List<ResponseBorrower> getAllBorrowers() {
        List<Borrower> borrowers = borrowerRepository.findAll();
        List<ResponseBorrower> resBorrowers = borrowers.stream()
                .map(borrowerMapper::toResponseBorrower)
                .collect(Collectors.toList());
        return resBorrowers;
    }

    @Override
    public ResponseBorrower getBorrowerById(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Borrower not found with id: "+id));
        return borrowerMapper.toResponseBorrower(borrower);
    }

    @Override
    public ResponseBorrower addBorrower(RequestBorrower requestBorrower) {
        Student student = studentRepository.findById(requestBorrower.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+ requestBorrower.getStudentId()));
        Book book = bookRepository.findById(requestBorrower.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id : "+ requestBorrower.getBookId()));
        Borrower borrower = borrowerMapper.toEntity(requestBorrower);
        borrower.setBook(book);
        borrower.setStudent(student);
        Borrower savedBorrower = borrowerRepository.save(borrower);
        return borrowerMapper.toResponseBorrower(savedBorrower);
    }

    @Override
    public ResponseBorrower updateBorrower(Long id, RequestBorrower requestBorrower) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: "+ id));
        borrowerMapper.updateEntityFromRequest(requestBorrower, borrower, studentRepository, bookRepository);
        Borrower updatedBorrower = borrowerRepository.save(borrower);
        return borrowerMapper.toResponseBorrower(updatedBorrower);
    }

    @Override
    public String deleteBorrower(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: "+ id));
        borrowerRepository.deleteById(id);
        return "Borrower id: "+ id +" deleted!";
    }

    @Override
    public List<ResponseStudent> getTopBorrowers() {
        List<Object[]> results = borrowerRepository.findTopBorrowers();
        List<Student> topBorrowers = new ArrayList<>();
        for(Object[] result : results){
            Long studentId = (Long) result[0];
            Integer borrowCount = ((Long) result[1]).intValue();

            Student student = studentRepository.findById(studentId).orElse(null);
            if (student != null){
                topBorrowers.add(student);
            }
        }
        List<ResponseStudent> resStudents = topBorrowers.stream().map(studentMapper::toResponseStudent).collect(Collectors.toList());
        return resStudents;
    }

    @Override
    public List<Map<String, Object>> getOverdueBorrowedBooks() {
        List<Object[]> response = borrowerRepository.findOverdueBorrowedBooks();
        List<Map<String, Object>> borrowers = new ArrayList<>();
        for (Object[] entry : response){
            Map<String, Object> borrower = new HashMap<>();
            borrower.put("id", entry[0]);
            borrower.put("student_name", entry[1]);
            borrower.put("book_title", entry[2]);
            Timestamp returnDate = (Timestamp) entry[3];
            OffsetDateTime dateTime = returnDate.toInstant().atOffset(ZoneOffset.UTC);
            String formatDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            borrower.put("return_data", formatDate);
            borrowers.add(borrower);
        }
        return borrowers;
    }
}
