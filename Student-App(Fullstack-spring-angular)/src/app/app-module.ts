import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { StudentService } from './services/student';
import { StudentComponent } from './components/student/student';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [App, StudentComponent],
  imports: [BrowserModule, AppRoutingModule,FormsModule,HttpClientModule],
  providers: [provideBrowserGlobalErrorListeners(), StudentService],
  bootstrap: [App],
})
export class AppModule {}
