package com.thurunu.springsecex.controller;

import com.thurunu.springsecex.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Thurunu", 85),
            new Student(2, "Mihiranga", 55),
            new Student(3, "Dilshan", 98)
    ));

    @GetMapping("/students")
    private List<Student> getStudents(){
        return students;
    }

    @PostMapping("/student")
    private Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
