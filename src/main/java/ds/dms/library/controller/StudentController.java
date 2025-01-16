package ds.dms.library.controller;

import ds.dms.library.dto.review.ResponseReview;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.services.borrower.BorrowerService;
import ds.dms.library.services.review.ReviewService;
import ds.dms.library.services.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    public final StudentService studentService;
    public final BorrowerService borrowerService;
    public final ReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseStudent>> getAllStudents(){
        List<ResponseStudent> students = studentService.getAllStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStudent> getStudentById(@PathVariable Long id){
        ResponseStudent student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/borrow-stats")
    public ResponseEntity<Integer> getCountTotalBooksByStudentId(@PathVariable Long id){
        Integer counter = studentService.getCountTotalBooksByStudentId(id);
        return ResponseEntity.ok(counter);
    }

    @GetMapping("/{id}/borrows-history")
    public ResponseEntity<List<Map<String, Object>>> getBorrowingHistoryByStudentId(@PathVariable Long id){
        List<Map<String, Object>> response = studentService.getBorrowingHistoryByStudentId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/borrows-history/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id){
        return studentService.generatePdf(id);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ResponseReview>> getReviewsByStudentId(@PathVariable Long id){
        List<ResponseReview> reviews = reviewService.getReviewsByStudentId(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/top-borrowers")
    public ResponseEntity<List<ResponseStudent>> getTopBorrowers(){
        List<ResponseStudent> students = borrowerService.getTopBorrowers();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStudent> addStudent(@RequestBody RequestStudent requestStudent){
        ResponseStudent student = studentService.addStudent(requestStudent);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseStudent> updateStudent(@PathVariable Long id, @RequestBody RequestStudent requestStudent){
        ResponseStudent student = studentService.updateStudent(id,requestStudent);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id){
        String message = studentService.deleteStudent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}
