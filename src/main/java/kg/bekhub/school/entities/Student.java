package kg.bekhub.school.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Min: 3, Max: 50")
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50, message = "Min: 3, Max: 50")
    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_teachers")
    @Size(min=1, message = "Min: 1")
    @NotEmpty
    private List<Teacher> teachers = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
