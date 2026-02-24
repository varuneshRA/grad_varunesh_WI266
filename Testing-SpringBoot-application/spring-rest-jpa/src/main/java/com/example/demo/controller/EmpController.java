package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.repo.EmpRepo;

@RestController
public class EmpController {

	@Autowired
	EmpRepo empRepo;

	@GetMapping("/greet")
	public String hi() {

		return "<h2> good morning <h2>";
	}

	@GetMapping("/")
	public String abc() {
		return "<h2> welcome to spring boot <h2>";
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployees(@PathVariable int id) {

		Optional<Employee> employee = empRepo.findById(id);

		if (employee.isPresent()) {
			return ResponseEntity.ok(employee.get());
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@GetMapping(path="/employees", produces = "application/xml")
	public Iterable<Employee> getAllEmployees() {
		return empRepo.findAll();
	}

	@PostMapping("/employees")
	public String getEmployees(@RequestBody Employee emp) {
		if (empRepo.existsById(emp.getEid())) {
			return "Employee id already exists";
		}
		empRepo.save(emp);
		return "Employee saved successfully";
	}

	@PutMapping("/employees/{id}")
	public String updateEmployees(@PathVariable int id, @RequestBody Employee emp) {

		if (id != emp.getEid()) {
			return "Employee id in the path and request body do not match";
		}
		if (!empRepo.existsById(id)) {
			return "Employee id does not exist";
		}
		empRepo.save(emp);
		return "Employee updated successfully";
	}

	@PatchMapping("/employees")
	public String partialUpdateEmployees(@RequestBody Employee emp) {
		//MANUALLY CHECK FOR NULL FIELDS IN THE REQUEST BODY AND UPDATE ONLY THOSE FIELDS
		
		Optional<Employee> existingEmpOpt = empRepo.findById(emp.getEid());
		if (!existingEmpOpt.isPresent()) {
			return "Employee id does not exist";
		}
		Employee existingEmp = existingEmpOpt.get();
		if (emp.getEid()!=0){
			existingEmp.setEid(emp.getEid());
		}
		if (emp.getName() != null) {
			existingEmp.setName(emp.getName());
		}
		if (emp.getAge() != 0) {
			existingEmp.setAge(emp.getAge());
		}
		if (emp.getSalary() != 0) {
			existingEmp.setSalary(emp.getSalary());
		}
		if (emp.getDesignation() != null && !emp.getDesignation().isEmpty()) {
			existingEmp.setDesignation(emp.getDesignation());
		}
		empRepo.save(existingEmp);
		return "Employee updated successfully";
	}

	@DeleteMapping("/employees/{id}")
	public String removeEmployees(@PathVariable int id) {
		if (!empRepo.existsById(id)) {
			return "Employee id does not exist";
		}
		empRepo.deleteById(id);
		return "Employee deleted successfully";
	}

	// Additional method to find employees by designation
	// using query parameter
	@GetMapping("/employees/role")
	public List<Employee> getEmployeesByDesignation(@RequestParam String desig) {
		System.out.println("Designation: " + desig);
		return empRepo.findByDesignationIgnoreCase(desig);
	}

	@GetMapping("/employees/salary")
	public List<Employee> getEmployeesBySalary(@RequestParam double salary) {
		return empRepo.findBySalaryLessThan(salary);
	}

	@GetMapping("/employees/age")
	public List<Employee> getEmployeesByAge(@RequestParam int age) {
		return empRepo.findByAgeGreaterThan(age);
	}

	@GetMapping("/employees/custom")	
	public List<Employee> getEmployeesByDesignationSortedBySalary(@RequestParam("role") String desig) {
		return empRepo.myCustomQuery(desig);
	}
	

}
