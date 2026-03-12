import { Component, OnInit } from '@angular/core';
import { Student } from '../../model/student.model';
import { StudentService } from '../../services/student';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  standalone: false
})
export class DashboardComponent implements OnInit {
  students: Student[] = [];
  isAdmin: boolean = false;
  currentStudent: Student = this.getEmptyStudent();
  isEditMode: boolean = false;

  constructor(private studentService: StudentService, private auth: AuthService) {}

  ngOnInit() {
    this.isAdmin = this.auth.isAdmin();
    this.loadStudents();
  }

  loadStudents() { this.students = this.studentService.getStudents(); }

  getEmptyStudent(): Student {
    return { regNo: '', rollNo: '', name: '', standard: '', school: '' };
  }

  editStudent(s: Student) {
    this.isEditMode = true;
    this.currentStudent = { ...s };
  }

  resetForm() {
    this.isEditMode = false;
    this.currentStudent = this.getEmptyStudent();
  }

  logout() { this.auth.logout(); }

  deleteStudent(regNo: string) {
  // Edge Case: Simple confirmation dialog
  const confirmed = confirm(`Are you sure you want to delete student with Reg No: ${regNo}?`);
  if (confirmed) {
    this.studentService.deleteStudent(regNo);
    this.loadStudents();
  }
}

saveStudent() {
  // Edge Case: Simple validation
  if (!this.currentStudent.regNo || !this.currentStudent.name) {
    alert("Please fill in the Reg No and Name at minimum.");
    return;
  }
  
  if (this.isEditMode) {
    this.studentService.updateStudent(this.currentStudent);
  } else {
    const added = this.studentService.addStudent(this.currentStudent);
    if (!added) alert("Error: A student with this Registration Number already exists.");
  }
  this.resetForm();
  this.loadStudents();
}
}