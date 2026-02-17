package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Student;
import com.example.demo.repo.StudentRepo;

@RestController
public class StudentController {

	@Autowired
	StudentRepo studentRepo;

	@GetMapping("/students")
	public Iterable<Student> getAllStudents() {
		return studentRepo.findAll();
	}

	@GetMapping("/students/{regno}")
	public Optional<Student> getStudents(@PathVariable int regno) {
		if (!studentRepo.existsById(regno)) {
			return Optional.empty();
		}
		return studentRepo.findById(regno);
	}
	
	@PostMapping("/students")
	public String getStudents(@RequestBody Student student) {
		if (studentRepo.existsById(student.getRegno())) {
			return "Student id already exists";
		}
		studentRepo.save(student);
		return "Student saved successfully";
	}

	@PutMapping("/students/{regno}")
	public String updateStudents(@PathVariable int regno, @RequestBody Student student) {

		if (regno != student.getRegno()) {
			return "Student id in the path and request body do not match";
		}
		if (!studentRepo.existsById(regno)) {
			return "Student id does not exist";
		}
		studentRepo.save(student);
		return "Student updated successfully";
	}

	@PatchMapping("/students/{regno}")
	public String partialUpdateStudents(@PathVariable int regno, @RequestBody Student student) {
		//MANUALLY CHECK FOR NULL FIELDS IN THE REQUEST BODY AND UPDATE ONLY THOSE FIELDS
		
		Optional<Student> existingStudentOpt = studentRepo.findById(regno);
		if (!existingStudentOpt.isPresent()) {
			return "Student id does not exist";
		}
		Student existingStudent = existingStudentOpt.get();
		if (student.getRollno() != 0) {
			existingStudent.setRollno(student.getRollno());
		}
		if (student.getStandard() != 0) {
			existingStudent.setStandard(student.getStandard());
		}
		if (student.getSchool() != null) {
			existingStudent.setSchool(student.getSchool());
		}
		if (student.getName() != null) {
			existingStudent.setName(student.getName());
		}
		if (student.getGender() != null) {
			existingStudent.setGender(student.getGender());
		}
		if (student.getPercentage() != 0) {
			existingStudent.setPercentage(student.getPercentage());
		}

		studentRepo.save(existingStudent);
		return "Student updated successfully";
	}

	@DeleteMapping("/students/{regno}")
	public String removeStudents(@PathVariable int regno) {
		if (!studentRepo.existsById(regno)) {
			return "Student id does not exist";
		}
		studentRepo.deleteById(regno);
		return "Student deleted successfully";
	}

	// using query parameter
	@GetMapping("/students/school")
	public List<Student> getStudentsBySchool(@RequestParam String name) {
		System.out.println("School: " + name);
		return studentRepo.findBySchoolIgnoreCase(name);
	}

	@GetMapping("/students/school/count")
	public long countBySchoolIgnoreCase(@RequestParam String name) {
		return studentRepo.countBySchoolIgnoreCase(name);
	}

	@GetMapping("/students/school/standard/count")
	public long countBySchoolAndStandard(@RequestParam int std) {
		return studentRepo.countByStandard(std);
	}

	@GetMapping("/students/result")
    public List<Student> getStudentsByResult(@RequestParam boolean pass) {
        if (pass) {
            // Returns list of students with 40% and above
            return studentRepo.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0);
        } else {
            // Returns list of students below 40%
            return studentRepo.findByPercentageLessThanOrderByPercentageDesc(40.0);
        }
    }

    @GetMapping("/students/strength")
    public long countByGenderAndStandard(@RequestParam String gender, @RequestParam int standard) {
        return studentRepo.countByGenderAndStandard(gender, standard);
    }

}
