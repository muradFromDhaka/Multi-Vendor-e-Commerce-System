import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  username!: string;
  password!: string;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  // login() {
  //   this.authService.login(this.username, this.password).subscribe({
  //     next: () => this.router.navigate(['/']),
  //     error: (err) => (this.error = 'invalid userName or password'),
  //   });
  // }


  login() {
  this.authService.login(this.username, this.password).subscribe({
    next: () => {

      if (this.authService.hasRole('ROLE_ADMIN')) {

        this.router.navigate(['/admin/adminDashboard']);

      } else if (this.authService.hasRole('ROLE_VENDOR')) {

        this.router.navigate(['/vendor/dashboard']);

      } else {

        this.router.navigate(['/']);

      }

    },

    error: () => {
      this.error = 'Invalid username or password';
    }

  });
}


}
