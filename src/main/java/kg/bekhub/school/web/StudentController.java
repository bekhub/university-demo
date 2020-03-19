package kg.bekhub.school.web;

import kg.bekhub.school.entities.Student;
import kg.bekhub.school.entities.Teacher;
import kg.bekhub.school.data.StudentRepository;
import kg.bekhub.school.data.TeacherRepository;
import kg.bekhub.school.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository,
                             TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Student student = studentRepository.findById(id).get();
        model.addAttribute("student", student);
        return "students/show";
    }

    @ModelAttribute("teachers")
    public List<Teacher> teachers() {
        List<Teacher> teachers = new ArrayList<>();
        teacherRepository.findAll().forEach(teachers::add);
        return teachers;
    }

    @PostMapping(value = "/{id}", params = "form")
    public String update(@Valid Student student,
                         Errors errors, Model model,
                         HttpServletRequest httpServletRequest)
    {
        log.info("Updating student");
        if(errors.hasErrors()) {
            model.addAttribute("student", student);
            return "students/update";
        }
        model.asMap().clear();
        studentRepository.save(student);
        return "redirect:/students/" + UrlUtil.encodeUrlPathSegment(student.getId()
        .toString(), httpServletRequest);
    }

    @GetMapping(value = "/{id}", params="form")
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", studentRepository.findById(id).get());
        return "students/update";
    }

    @PostMapping(value = "/{id}", params = "del")
    public String delete(@PathVariable("id") Integer id) {
        log.info("Deleting student " + studentRepository.findById(id).get().toString());
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping(value = "/{id}", params = "del")
    public String deleteForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("obj", studentRepository.findById(id).get());
        model.addAttribute("path", "students");
        return "/delete";
    }

    @PostMapping
    public String create(@Valid Student student, Errors errors, Model model)
    {
        log.info("Creating student");
        if(errors.hasErrors()) {
            model.addAttribute("student", student);
            return "students/create";
        }
        model.asMap().clear();
        log.info("Student id: " + student.getId());
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping(params = "form")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/create";
    }
}