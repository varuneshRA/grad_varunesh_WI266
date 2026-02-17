package com.example.demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    // 1. DEFAULT METHOD (Derived): Find by school ignoring case
    List<Student> findBySchoolIgnoreCase(String school);

    // 2. DEFAULT METHOD (Derived): Count by school ignoring case
    long countBySchoolIgnoreCase(String school);

    // 3. DEFAULT METHOD (Derived): Count by standard
    long countByStandard(int standard);

    // 4. DEFAULT METHOD (Derived): Count by Gender and Standard
    // Spring creates the AND logic automatically
    long countByGenderAndStandard(String gender, int standard);

    // 5. Required for "Greater Than" comparison and "Order By"
    List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(double cutoff);

    // 6.Required for "Less Than" comparison and "Order By"
    List<Student> findByPercentageLessThanOrderByPercentageDesc(double cutoff);
}