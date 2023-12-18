package com.studentreg.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentreg.demo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    List<Student> findByDeleteFlagFalse();

    @Override
    default void deleteById(Integer id) {
        findById(id).ifPresent(user -> {
            user.setDeleteFlag(true);
            save(user);
        });
    }
}
