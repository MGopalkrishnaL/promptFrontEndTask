package com.Wipro.Student.controller;

import com.Wipro.Student.DTO.RequestData;
import com.Wipro.Student.model.Student;
import com.Wipro.Student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/getAllStudent")
    public ResponseEntity<List<Student>> getAllStudents() {
        return studentService.getAllStudent();
    }
    @PostMapping("/saveStudent")
    public ResponseEntity<String> saveStudent(@RequestBody RequestData requestData){
        return studentService.saveStudent(requestData);
    }
}
