package kg.bekhub.school.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s, %s", name, location);
    }
}
