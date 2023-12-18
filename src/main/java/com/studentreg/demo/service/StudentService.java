package com.studentreg.demo.service;

import java.util.List;
import java.util.Optional;

import com.studentreg.demo.model.Student;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Integer id);
    Student saveStudent(Student student);
    String deleteStudent(Integer id);
}
