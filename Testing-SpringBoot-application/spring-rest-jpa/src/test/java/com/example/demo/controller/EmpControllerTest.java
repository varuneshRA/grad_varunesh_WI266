package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entities.Employee;
import com.example.demo.repo.EmpRepo;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmpRepo empRepo; // The dependency

    @InjectMocks
    private EmpController controller; // The class under test

    @Test
    void testGetEmployee_Found() {
        // 1. Arrange
        Employee emp = new Employee();
		emp.setEid(1);
		emp.setName("John Doe");
        when(empRepo.findById(1)).thenReturn(Optional.of(emp));

        // 2. Act
        ResponseEntity<Employee> response = controller.getEmployees(1);

        // 3. Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        verify(empRepo, times(1)).findById(1);
    }

    @Test
    void testGetEmployee_NotFound() {
        // 1. Arrange
        when(empRepo.findById(99)).thenReturn(Optional.empty());

        // 2. Act
        ResponseEntity<Employee> response = controller.getEmployees(99);

        // 3. Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(empRepo).findById(99);
    }
}