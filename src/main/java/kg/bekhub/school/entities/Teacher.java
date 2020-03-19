package kg.bekhub.school.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @NotNull
    private University university;

    @NotBlank
    @Size(min = 3, max = 40)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 40)
    private String lastName;

    @ManyToMany(mappedBy = "teachers", cascade = CascadeType.PERSIST)
    private List<Student> student = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}