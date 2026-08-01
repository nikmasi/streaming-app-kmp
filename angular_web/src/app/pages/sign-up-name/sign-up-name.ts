import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-sign-up-name',
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-up-name.html',
  styleUrl: './sign-up-name.css',
})
export class SignUpName {
  firstname=""
  lastname=""
  email=""
  router = inject(Router)

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
  }

  sign_up(){
    this.router.navigate(['/sign-up-password'], {
      queryParams: {
        email: this.email,
        firstname: this.firstname,
        lastname: this.lastname
      }
    });
  }

  sign_in(){
    this.router.navigate(['/signIn'])
  }

}
