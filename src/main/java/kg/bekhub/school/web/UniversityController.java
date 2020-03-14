package kg.bekhub.school.web;

import kg.bekhub.school.entities.University;
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
@RequestMapping("/universities")
public class UniversityController {

    private UniversityRepository universityRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public UniversityController(UniversityRepository universityRepository, TeacherRepository teacherRepository) {
        this.universityRepository = universityRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<University> universities = new ArrayList<>();
        universityRepository.findAll().forEach(universities::add);
        model.addAttribute("universities", universities);
        return "universities/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        University university = universityRepository.findById(id).get();
        model.addAttribute("university", university);
        model.addAttribute("teachers", teacherRepository.findAllByUniversityId(university.getId()));
        return "universities/show";
    }

    @PostMapping(value = "/{id}", params = "form")
    public String update(@Valid University university,
                         Errors errors, Model model,
                         HttpServletRequest httpServletRequest)
    {
        log.info("Updating university");
        if(errors.hasErrors()) {
            model.addAttribute("university", university);
            return "universities/update";
        }
        model.asMap().clear();
        universityRepository.save(university);
        return "redirect:/universities/" + UrlUtil.encodeUrlPathSegment(university.getId()
                .toString(), httpServletRequest);
    }

    @GetMapping(value = "/{id}", params="form")
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("university", universityRepository.findById(id).get());
        return "universities/update";
    }

    @PostMapping
    public String create(@Valid University university, Errors errors, Model model)
    {
        log.info("Creating university");
        if(errors.hasErrors()) {
            model.addAttribute("university", university);
            return "universities/create";
        }
        model.asMap().clear();
        log.info("University id: " + university.getId());
        universityRepository.save(university);
        return "redirect:/universities";
    }

    @GetMapping(params = "form")
    public String createForm(Model model) {
        model.addAttribute("university", new University());
        return "universities/create";
    }
}
