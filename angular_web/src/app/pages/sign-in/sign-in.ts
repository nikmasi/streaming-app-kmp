import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
})
export class SignIn {
  email = '';
  password = '';
  router = inject(Router);

  authService = inject(Auth);

  signIn() {
    this.authService.authenticate({
      email: this.email,
      password: this.password,
    })
    .subscribe({
      next: (response) => {

        console.log("Sign In success", response);
        localStorage.setItem("access_token", response.access_token);
        localStorage.setItem("refresh_token", response.refresh_token);

        this.router.navigate(['/home']);
      },

      error: (error) => {
        console.log("Registration failed", error);
      }
    });
  }

}