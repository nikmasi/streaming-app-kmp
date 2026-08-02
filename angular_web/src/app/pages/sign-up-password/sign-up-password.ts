import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Auth } from '../../service/auth';

@Component({
  selector: 'app-sign-up-password',
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-up-password.html',
  styleUrl: './sign-up-password.css',
})
export class SignUpPassword {

  password=""
  router = inject(Router)
  email = '';
  firstname='';
  lastname='';

  constructor(private route: ActivatedRoute) {}

  authService = inject(Auth);

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
      this.firstname = params['firstname'];
      this.lastname = params['lastname'];
    });
  }

  sign_in(){
    this.router.navigate(['/signIn'])
  }

  sign_up(){
    this.authService.register({
      firstname: this.firstname,
      lastname: this.lastname,
      email: this.email,
      password: this.password,
      role: "USER"
    })
    .subscribe({
      next: (response) => {
        localStorage.setItem("access_token", response.access_token);
        localStorage.setItem("refresh_token", response.refresh_token);

        localStorage.setItem("email", this.email);

        this.router.navigate(['/home']);
      },

      error: (error) => {
        console.log("Registration failed", error);
      }
    });
  }

}
