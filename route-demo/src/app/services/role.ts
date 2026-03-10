import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Role {
  private role: string = "";

  constructor() {}

  getRole(): string {
    return this.role;
  }

  setRole(newRole: string): void {
    this.role = newRole;
  }
}
