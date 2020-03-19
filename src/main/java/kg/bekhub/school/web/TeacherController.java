package kg.bekhub.school.web;

import kg.bekhub.school.entities.Student;
import kg.bekhub.school.entities.Teacher;
import kg.bekhub.school.entities.University;
import kg.bekhub.school.data.StudentRepository;
import kg.bekhub.school.data.TeacherRepository;
import kg.bekhub.school.data.UniversityRepository;
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
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherRepository teacherRepository;
    private UniversityRepository universityRepository;
    private StudentRepository studentRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository,
                             UniversityRepository universityRepository,
                             StudentRepository  studentRepository) {
        this.teacherRepository = teacherRepository;
        this.universityRepository = universityRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<Teacher> teachers = new ArrayList<>();
        teacherRepository.findAll().forEach(teachers::add);
        model.addAttribute("teachers", teachers);
        return "/teachers/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Teacher teacher = teacherRepository.findById(id).get();
        model.addAttribute("teacher", teacher);
        return "/teachers/show";
    }

    @ModelAttribute("universities")
    List<University> universities() {
        List<University> universities = new ArrayList<>();
        universityRepository.findAll().forEach(universities::add);
        return universities;
    }

    @PostMapping(value = "/{id}", params = "form")
    public String update(@Valid Teacher teacher,
                         Errors errors, Model model,
                         HttpServletRequest httpServletRequest)
    {
        log.info("Updating teacher");
        if(errors.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teachers/update";
        }
        model.asMap().clear();
        teacherRepository.save(teacher);
        return "redirect:/teachers/" + UrlUtil.encodeUrlPathSegment(teacher.getId()
                .toString(), httpServletRequest);
    }

    @GetMapping(value = "/{id}", params="form")
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("teacher", teacherRepository.findById(id).get());
        return "teachers/update";
    }

    @PostMapping(value = "/{id}", params = "del")
    public String delete(@PathVariable("id") Integer id) {
        log.info("Deleting teacher " + teacherRepository.findById(id).get().toString());
        try {
            teacherRepository.deleteById(id);
        } catch (Exception e) {
            return "/del_error";
        }
        return "redirect:/teachers";
    }

    @GetMapping(value = "/{id}", params = "del")
    public String deleteForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("obj", teacherRepository.findById(id).get());
        model.addAttribute("path", "teachers");
        return "/delete";
    }

    @PostMapping
    public String create(@Valid Teacher teacher, Errors errors, Model model)
    {
        log.info("Creating teacher");
        if(errors.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teachers/create";
        }
        model.asMap().clear();
        log.info("Teacher id: " + teacher.getId());
        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping(params = "form")
    public String createForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teachers/create";
    }
}
