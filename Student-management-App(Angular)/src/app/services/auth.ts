import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserRole } from '../model/student.model';


@Injectable({ providedIn: 'root' })
export class AuthService {
  private userRole: UserRole = null;

  constructor(private router: Router) {}

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      this.setSession('ADMIN');
      return true;
    } else if (username === 'staff' && password === 'staff') {
      this.setSession('STAFF');
      return true;
    }
    return false;
  }

  private setSession(role: UserRole) {
    this.userRole = role;
    localStorage.setItem('userRole', role!);
    this.router.navigate(['/dashboard']);
  }

  logout() {
    this.userRole = null;
    localStorage.removeItem('userRole');
    this.router.navigate(['/login']);
  }

  getRole(): UserRole {
    return this.userRole || (localStorage.getItem('userRole') as UserRole);
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }

  isLoggedIn(): boolean {
    return !!this.getRole();
  }
}