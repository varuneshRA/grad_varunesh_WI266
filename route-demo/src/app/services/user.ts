import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class User {
  private name: string = 'Guest';

  constructor() {}

  getName(): string {
    return this.name;
  }

  setName(newName: string): void {
    this.name = newName;
  }
}
