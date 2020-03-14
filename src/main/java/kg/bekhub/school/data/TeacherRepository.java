package kg.bekhub.school.data;

import kg.bekhub.school.entities.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
    List<Teacher> findAllByUniversityId(int id);
}