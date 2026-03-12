import { Injectable } from '@angular/core';
import { Student } from '../model/student.model';


@Injectable({ providedIn: 'root' })
export class StudentService {
  private students: Student[] = [
    { regNo: 'REG101', rollNo: '1', name: 'John Doe', standard: '10th', school: 'Wissen International' },
    { regNo: 'REG102', rollNo: '2', name: 'Jane Smith', standard: '12th', school: 'Wissen International' }
  ];

  getStudents(): Student[] {
    return this.students;
  }

  addStudent(student: Student): boolean {
    if (this.students.find(s => s.regNo === student.regNo)) return false;
    this.students.push(student);
    return true;
  }

  updateStudent(updatedStudent: Student) {
    const index = this.students.findIndex(s => s.regNo === updatedStudent.regNo);
    if (index !== -1) this.students[index] = updatedStudent;
  }

  deleteStudent(regNo: string) {
    this.students = this.students.filter(s => s.regNo !== regNo);
  }
}