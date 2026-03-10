import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';


@Component({
  selector: 'app-login',
  standalone: false,
  template: `
    <div class="vh-100 d-flex align-items-center justify-content-center bg-light">
      <div class="card shadow-lg border-0" style="width: 24rem; border-radius: 15px;">
        <div class="card-body p-5">
          <div class="text-center mb-4">
            <h2 class="fw-bold text-primary">StudentApp</h2>
            <p class="text-muted">Please enter your credentials</p>
          </div>
          
          <div class="mb-3">
            <label class="form-label fw-semibold">Username</label>
            <input type="text" [(ngModel)]="username" class="form-control form-control-lg" placeholder="admin or staff">
          </div>
          
          <div class="mb-4">
            <label class="form-label fw-semibold">Password</label>
            <input type="password" [(ngModel)]="password" class="form-control form-control-lg" placeholder="••••••••">
          </div>

          <div *ngIf="errorMessage" class="alert alert-danger py-2 small mb-3">
            {{ errorMessage }}
          </div>

          <button class="btn btn-primary btn-lg w-100 shadow-sm" (click)="onLogin()">
            Sign In
          </button>
          
          <div class="mt-4 text-center">
            <small class="text-muted">Wissen Technology Training Portal</small>
          </div>
        </div>
      </div>
    </div>
  `
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private auth: AuthService) {}

  onLogin() {
    if (!this.auth.login(this.username, this.password)) {
      this.errorMessage = 'Invalid username or password';
    }
  }
}