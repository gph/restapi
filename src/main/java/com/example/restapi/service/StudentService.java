package com.example.restapi.service;

import com.example.restapi.model.Student;
import com.example.restapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email){
        return studentRepository.findStudentByEmail(email);
    }

    public String addStudent(Student student){
        if(studentRepository.findStudentByEmail(student.getEmail()).isPresent()){
            return "Already exist!";
        }else{
            studentRepository.insert(student);
            return "Student with email " + student.getEmail() + " was added!";
        }
    }

    public String deleteStudent(String id) {
        if(studentRepository.findById(id).isPresent()){
            studentRepository.deleteById(id);
            return "Student " + id + " deleted!";
        }else{
            return "Student " + id + " doesn't exist!";
        }
    }
}
