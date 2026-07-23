import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { VendorNavbarComponent } from './shared/navbar/vendor-navbar/vendor-navbar.component';
import { CustomerNavbarComponent } from './shared/navbar/customer-navbar/customer-navbar.component';
import { PublicNavbarComponent } from './shared/navbar/public-navbar/public-navbar.component';
import { AdminNavbarComponent } from './shared/navbar/admin-navbar/admin-navbar.component';
import { TestComponent } from './test/test.component';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AdminNavbarComponent,
    VendorNavbarComponent,
    CustomerNavbarComponent,
    PublicNavbarComponent,
    TestComponent,
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
],
  bootstrap: [AppComponent]
})
export class AppModule { }
