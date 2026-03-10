import { Component, Inject, Injectable } from '@angular/core';
import { User } from '../services/user';

@Component({
  selector: 'app-welcome',
  standalone: false,
  templateUrl: './welcome.html',
  styleUrl: './welcome.css',
})

export class Welcome {
  user: User;

  constructor(user: User) {
    this.user = user;
  }

}
