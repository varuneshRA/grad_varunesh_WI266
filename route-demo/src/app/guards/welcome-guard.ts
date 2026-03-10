import { CanActivateFn, Router } from '@angular/router';
import { User } from '../services/user';
import { inject } from '@angular/core';
import { Role } from '../services/role';

export const welcomeGuard: CanActivateFn = (route, state) => {
  let us:User = inject(User);
  let rs:Role = inject(Role);
  let router:Router = inject(Router);

  console.log('welcome guard called');
  console.log(us.getName());

  if (us.getName() === 'Guest') {
    alert('Please login first!');
    router.navigate(['/login']);
    return false;
  }

  if (route.data[0] === rs.getRole() || route.data[1] === rs.getRole()) {
    return true;
  }

  return false;
};
