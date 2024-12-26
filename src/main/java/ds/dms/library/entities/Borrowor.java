package ds.dms.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class Borrowor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date borrowedDate;
    private Date returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
