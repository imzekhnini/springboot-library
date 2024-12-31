package ds.dms.library.controller;

import ds.dms.library.dao.BorrowerRepository;
import ds.dms.library.dto.borrower.RequestBorrower;
import ds.dms.library.dto.borrower.ResponseBorrower;
import ds.dms.library.services.borrower.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrowers")
public class BorrowerController {
    public final BorrowerService borrowerService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseBorrower>> getAllBorrowers(){
        List<ResponseBorrower> borrowers = borrowerService.getAllBorrowers();
        return ResponseEntity.ok(borrowers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBorrower> getBorrowerById(@PathVariable Long id){
        ResponseBorrower borrower = borrowerService.getBorrowerById(id);
        return ResponseEntity.ok(borrower);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBorrower> addBorrower(@RequestBody RequestBorrower requestBorrower){
        ResponseBorrower borrower = borrowerService.addBorrower(requestBorrower);
        return ResponseEntity.ok(borrower);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseBorrower> updateBorrower(@PathVariable Long id, @RequestBody RequestBorrower requestBorrower){
        ResponseBorrower borrower = borrowerService.updateBorrower(id, requestBorrower);
        return ResponseEntity.ok(borrower);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteBorrower(@PathVariable Long id){
        String message = borrowerService.deleteBorrower(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}
