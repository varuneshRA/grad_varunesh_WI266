package com.example.demo.controller;

import com.example.demo.entities.Student;
import com.example.demo.repo.d1.StudentRepoH2;
import com.example.demo.repo.d2.StudentRepoPostgres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepoH2 h2Repo;

    @Autowired
    private StudentRepoPostgres pgRepo;

    @GetMapping("/test")
    @ResponseBody
    public String getMethodName() {
        return "working";
    }

    @RequestMapping("/")
    public String xyz(Model model) {
        model.addAttribute("student", new Student());
        return "home";
    }

    @PostMapping("/insert")
    public String insertData(
            @RequestParam("rollNo") Long roll,
            @RequestParam("name") String name,
            @RequestParam("standard") String std,
            @RequestParam("schoolName") String school) {

        if (h2Repo.existsById(roll) || pgRepo.existsById(roll)) {
            return "redirect:/error-page";
        }

        Student s = new Student();
        s.setRollNo(roll);
        s.setName(name);
        s.setStandard(std);
        s.setSchoolName(school);

        h2Repo.save(s);
        pgRepo.save(s);

        return "redirect:/?success";
    }

    // mapping for error page
    @GetMapping("/error-page")
    public String showErrorPage() {
        return "error-page";
    }
}
