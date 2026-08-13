import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
})
export class SignIn {
  constructor(
    private translate: TranslateService
  ) {}
  
  email = '';
  password = '';
  router = inject(Router);

  error = '';

  authService = inject(Auth);

  signIn() {
    this.authService.authenticate({
      email: this.email,
      password: this.password,
    })
    .subscribe({
      next: (response) => {
        localStorage.setItem("access_token", response.access_token);
        localStorage.setItem("refresh_token", response.refresh_token);

        localStorage.setItem("email", this.email);

        this.router.navigate(['/home']);
      },

      error: (error) => {
        this.error ="Registration failed"
      }
    });
  }

}