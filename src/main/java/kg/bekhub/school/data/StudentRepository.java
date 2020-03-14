package kg.bekhub.school.data;

import kg.bekhub.school.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}