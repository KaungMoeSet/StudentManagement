package com.studentreg.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentreg.demo.model.Course;
import com.studentreg.demo.model.Gender;
import com.studentreg.demo.model.Student;
import com.studentreg.demo.service.CourseService;
import com.studentreg.demo.service.JasperReportService;
import com.studentreg.demo.service.StudentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    JasperReportService jasperReportService;

    private void populateModelAttributes(Model model) {
        model.addAttribute("genders", Gender.values());
    
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
    
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
    }

    @GetMapping
    String getAllStudents(Model model) {

        model.addAttribute("student", new Student());

        populateModelAttributes(model);

        return "students";
    }

    @PostMapping("/new")
    String createStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            populateModelAttributes(model);

            return "students";
        } else {
            model.addAttribute("student", new Student());

            populateModelAttributes(model);

            studentService.saveStudent(student);
            return "redirect:/students";
        }      
    }

    @GetMapping("{id}")
    public String editStudent(Model model, @PathVariable("id") Integer id) {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);

        populateModelAttributes(model);
            
        return "students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(Model model, @PathVariable("id") Integer id) {
        studentService.deleteStudent(id);
        
        populateModelAttributes(model);

        return "redirect:/students";
    }
    
    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport() {
        byte[] pdfReport = jasperReportService.generateReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("inline", "studentsReport.pdf");

        return new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
    }
}
