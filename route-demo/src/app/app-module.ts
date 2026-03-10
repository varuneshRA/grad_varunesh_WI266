import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { AboutUs } from './about-us/about-us';
import { Home } from './home/home';
import { ContactUs } from './contact-us/contact-us';
import { Login } from './login/login';
import { Welcome } from './welcome/welcome';
import { Services } from './services/services';
import { Menu } from './menu/menu';
import { Failure } from './failure/failure';
import { User } from './services/user';

@NgModule({
  declarations: [App, AboutUs, Home, ContactUs, Login, Welcome, Services, Menu, Failure],
  imports: [BrowserModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners(),User],
  bootstrap: [App],
})
export class AppModule {}
