import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student';

@Component({
  selector: 'app-student',
  templateUrl: './student.html',
  styleUrls: ['./student.css'],
  standalone: false
})
export class StudentComponent implements OnInit {
  students: Student[] = [];
  currentStudent: Student = this.getEmptyStudent();
  searchSchool: string = '';
  stdToCount: number = 0;
  countBySchoolName: string = '';
  stdCountResult: number | null = null;
  schoolCountResult: number | null = null;
  isEdit: boolean = false;

  constructor(private ss: StudentService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void { this.loadAll(); }

  getEmptyStudent(): Student {
    return { regno: 0, rollno: 0, name: '', standard: 0, school: '', gender: 'Male', percentage: 0 };
  }

  loadAll() {
    this.ss.getAll().subscribe({
      next: (res) => {
        this.students = res;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  onSubmit() {
    if (!this.validateForm()) return;
    if (this.isEdit) {
      this.ss.update(this.currentStudent.regno, this.currentStudent).subscribe(msg => {
        alert(msg);
        this.resetForm();
        this.loadAll();
      });
    } else {
      this.ss.add(this.currentStudent).subscribe(msg => {
        alert(msg);
        if (msg.includes("successfully")) {
          this.resetForm();
          this.loadAll();
        }
      });
    }
  }

  editStudent(s: Student) {
    this.currentStudent = { ...s };
    this.isEdit = true;
    this.cdr.detectChanges();
  }

  deleteStudent(regno: number) {
    if (confirm('Delete this record?')) {
      this.ss.delete(regno).subscribe({
        next: (msg) => {
          alert(msg);
          this.loadAll();
        },
        error: (err) => console.error(err)
      });
    }
  }

  filterBySchool() {
    if (this.searchSchool) {
      this.ss.getBySchool(this.searchSchool).subscribe(res => {
        this.students = res;
        this.cdr.detectChanges();
      });
    } else {
      this.loadAll();
    }
  }

  filterByResult(pass: boolean) {
    this.ss.getByResult(pass).subscribe(res => {
      this.students = res;
      this.cdr.detectChanges();
    });
  }

  resetForm() {
    this.currentStudent = this.getEmptyStudent();
    this.isEdit = false;
    this.cdr.detectChanges();
  }

  validateForm(): boolean {
    if (this.currentStudent.regno === 0) {
      alert('Reg No is required');
      return false;
    }
    if (!this.currentStudent.name.trim()) {
      alert('Name is required');
      return false;
    }
    if (this.currentStudent.rollno <= 0) {
      alert('Roll No must be greater than 0');
      return false;
    }
    if (this.currentStudent.standard <= 0) {
      alert('Standard must be greater than 0');
      return false;
    }
    if (!this.currentStudent.school.trim()) {
      alert('School is required');
      return false;
    }
    if (this.currentStudent.percentage < 0 || this.currentStudent.percentage > 100) {
      alert('Percentage must be between 0 and 100');
      return false;
    }
    return true;
  }

  fetchStandardCount() {
    this.ss.getStandardCount(this.stdToCount).subscribe({
      next: (count) => {
        this.stdCountResult = count;
        alert(`Standard ${this.stdToCount} has ${count} students`);
        this.cdr.detectChanges();
      },
      error: (err) => alert("Error fetching standard count")
    });
  }

  fetchSchoolCount(school: string) {
    if (!school) {
      alert('Please enter a school name');
      return;
    }
    this.ss.getSchoolCount(school).subscribe({
      next: (count) => {
        this.schoolCountResult = count;
        alert(`${school} has ${count} students`);
        this.cdr.detectChanges();
      },
      error: (err) => alert("Error fetching school count")
    });
  }
}