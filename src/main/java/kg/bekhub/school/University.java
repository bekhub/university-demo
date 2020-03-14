package kg.bekhub.school;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Teacher> teachers = new HashSet<>();

    @Override
    public String toString() {
        return String.format("%s, %s", name, location);
    }
}
