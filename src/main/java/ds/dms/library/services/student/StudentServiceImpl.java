package ds.dms.library.services.student;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ds.dms.library.dao.BorrowerRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.student.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    public static final Integer MAX_PAGE_SIZE = 10;

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
    public ResponseEntity<byte[]> generatePdf(Long id){
        try {
            Student student = studentRepository.findById(id).orElse(null);
            if(student == null){
                throw new RuntimeException("Student with id: "+ id +"does not exist!");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            List<Map<String, Object>> history = getBorrowingHistoryByStudentId(id);

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(student.getName()+"'s borrowing history.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Paragraph studentDetails = new Paragraph();
            studentDetails.add("Name: " + student.getName() + "\n");
            studentDetails.add("Email: " + student.getEmail() + "\n");
            studentDetails.add("Age: " + student.getAge() + "\n");
            studentDetails.setSpacingAfter(10);
            document.add(studentDetails);

            PdfPTable table = new PdfPTable(3);
            table.addCell("Book title");
            table.addCell("Borrowed Date");
            table.addCell("Return Date");

            if(history.isEmpty()){
                document.add(new Paragraph("This student has no borrowing history."));
            }else{
                for(Map<String, Object> entry : history){
                    table.addCell((String) entry.get("book_title"));

                    String borrowedDate = (String) entry.get("borrowed_date");
                    String returnDate = (String) entry.get("return_date");

                    table.addCell(borrowedDate != null ? borrowedDate : "N/A");
                    table.addCell(returnDate != null ? returnDate : "N/A");
                }
            }

            document.add(table);

            document.close();

            String filename = student.getName().replace(" ", "_") + "_borrowing_history.pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(outputStream.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<ResponseStudent> getStudentsPaginated(Integer page, Integer size, String sortField, String sortDirection) {
        size = size > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : size;
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(studentMapper::toResponseStudent);
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
