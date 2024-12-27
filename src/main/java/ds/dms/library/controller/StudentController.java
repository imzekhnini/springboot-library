package ds.dms.library.controller;

import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.services.student.StudentService;
import lombok.RequiredArgsConstructor;
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
