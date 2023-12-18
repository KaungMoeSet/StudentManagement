package com.studentreg.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentreg.demo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    
}
