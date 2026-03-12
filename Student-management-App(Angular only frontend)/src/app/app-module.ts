import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { AuthService } from './services/auth';
import { StudentService } from './services/student';
import { LoginComponent } from './components/login/login';
import { DashboardComponent } from './components/dashboard/dashboard';
import { FormsModule } from '@angular/forms';
import { NotFoundComponent } from './components/not-found/not-found';

@NgModule({
  declarations: [App, LoginComponent, DashboardComponent, NotFoundComponent],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [provideBrowserGlobalErrorListeners(), AuthService, StudentService],
  bootstrap: [App],
})
export class AppModule {}
