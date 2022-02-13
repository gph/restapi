package com.example.restapi.controller;

import com.example.restapi.model.Student;
import com.example.restapi.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("api/v1/students")
    public List<Student> fetchAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("api/v1/student")
    @ResponseBody
    public Optional<Student> getStudent(@RequestParam String email){
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("api/v1/student/{id}")
    @ResponseBody
    public Optional<Student> getStudentById(@PathVariable String id){
        return studentService.getStudentById(id);
    }

    @PostMapping(value = "api/v1/student",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @DeleteMapping(value = "api/v1/student/{id}")
    public String deleteStudent(@PathVariable String id){
        return studentService.deleteStudent(id);
    }


}
