import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../services/user';
import { Role } from '../services/role';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private user: User, private router: Router,private role: Role) { }

  abc(event: any) {
    event.preventDefault();
    let username = event.target.elements[0].value;
    let password = event.target.elements[1].value;
    let role = event.target.elements[2].value;
    console.log(username);
    console.log(password);
    console.log(role);

    if (username === 'admin' && password === 'admin') {
      this.user.setName(username);
      this.role.setRole(role);
      
      this.router.navigate(['welcome']);
      
    } else {
      alert('Login failed');
      this.router.navigate(['failure']);
    }
    console.log('------------------------------');
  }
}
