package ds.dms.library.services.borrower;

import ds.dms.library.dao.BookRepository;
import ds.dms.library.dao.BorrowerRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.borrower.RequestBorrower;
import ds.dms.library.dto.borrower.ResponseBorrower;
import ds.dms.library.entities.Book;
import ds.dms.library.entities.Borrower;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.borrower.BorrowerMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowerServiceImpl implements BorrowerService {
    public final BorrowerRepository borrowerRepository;
    public final BorrowerMapper borrowerMapper;
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
}
