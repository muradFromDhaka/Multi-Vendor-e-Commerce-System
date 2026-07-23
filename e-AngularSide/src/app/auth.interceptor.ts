// import { Injectable } from '@angular/core';
// import {
//   HttpRequest,
//   HttpHandler,
//   HttpInterceptor
// } from '@angular/common/http';
// import { AuthService } from './services/auth.service';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {

//   constructor(private authService: AuthService) {}

//   intercept(req: HttpRequest<any>, next: HttpHandler) {
//   if (req.url.includes('/auth/login') || req.url.includes('/auth/signin')) {
//     return next.handle(req);
//   }

//   const token = this.authService.getToken();

//   if (token) {
//     const cloned = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     return next.handle(cloned);
//   }

//   return next.handle(req);
// }

// }


import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpInterceptor,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Login API তে token পাঠাবেন না
    if (req.url.includes('/auth/login') || req.url.includes('/auth/signin')) {
      return next.handle(req);
    }

    const token = this.authService.getToken();

    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req).pipe(

      catchError((error: HttpErrorResponse) => {

        if (error.status === 401) {

          // যদি backend TOKEN_EXPIRED পাঠায়
          if (error.error?.error === 'TOKEN_EXPIRED') {

            alert('Session expired. Please login again.');

            this.authService.logOut();

            this.router.navigate(['/login']);
          }

        }

        return throwError(() => error);
      })

    );
  }
}