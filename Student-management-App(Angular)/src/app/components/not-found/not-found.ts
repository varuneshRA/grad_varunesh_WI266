import { Component } from '@angular/core';

@Component({
  selector: 'app-not-found',
  standalone: false,
  template: `
    <div class="vh-100 d-flex align-items-center justify-content-center bg-light">
      <div class="text-center p-5 shadow-sm bg-white" style="border-radius: 20px; max-width: 500px;">
        <h1 class="display-1 fw-bold text-primary">404</h1>
        <h2 class="mb-3">Oops! Page Not Found</h2>
        <p class="text-muted mb-4">
          The page you are looking for might have been removed, had its name changed, 
          or is temporarily unavailable.
        </p>
        <a routerLink="/login" class="btn btn-primary btn-lg shadow-sm px-5">
          Back to Login
        </a>
      </div>
    </div>
  `
})
export class NotFoundComponent {}