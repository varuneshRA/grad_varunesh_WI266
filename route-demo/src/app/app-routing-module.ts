import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './home/home';
import { Welcome } from './welcome/welcome';
import { AboutUs } from './about-us/about-us';
import { Login } from './login/login';
import { Services } from './services/services';
import { ContactUs } from './contact-us/contact-us';
import { Failure } from './failure/failure';
import { welcomeGuard } from './guards/welcome-guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'welcome',
    component: Welcome,
    canActivate:[welcomeGuard],
    data:["MANAGER","EMPLOYEE"]
  },
  {
    path: 'home',
    component: Home
  },
  {
    path: 'about',
    component: AboutUs
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'services',
    component: Services
  },
  {
    path: 'contact',
    component: ContactUs
  },
  {
    path: '**',
    component: Failure
  },
  {
    path: 'failure',
    component: Failure
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
