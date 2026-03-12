import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class StudentService {
  private baseUrl = 'http://localhost:8484/students';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Student[]> {
    return this.http.get<Student[]>(this.baseUrl);
  }

  getById(regno: number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${regno}`);
  }

  add(student: Student): Observable<string> {
    return this.http.post(this.baseUrl, student, { responseType: 'text' });
  }

  update(regno: number, student: Student): Observable<string> {
    return this.http.put(`${this.baseUrl}/${regno}`, student, { responseType: 'text' });
  }

  delete(regno: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${regno}`, { responseType: 'text' });
  }

  // Query Parameter Methods
  getBySchool(schoolName: string): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/school`, { params: { name: schoolName } });
  }

  getByResult(isPass: boolean): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/result`, { params: { pass: isPass.toString() } });
  }

  // Add this to your StudentService class
  getStandardCount(std: number): Observable<number> {
    // Pass 'std' as a query parameter
    return this.http.get<number>(`${this.baseUrl}/school/standard/count`, { 
      params: { std: std.toString() } 
    });
  }

  getSchoolCount(name: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/school/count`, { 
      params: { name: name } 
    });
  }

}