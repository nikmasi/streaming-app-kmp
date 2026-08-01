import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  imports: [FormsModule],
  templateUrl: './sign-up.html',
  styleUrl: './sign-up.css',
})
export class SignUp {

  router = inject(Router)

  email = '';

  sign_in(){
    this.router.navigate(['/signIn'])
  }

  sign_up(){ 
    this.router.navigate(['/sign-up-name'], {
      queryParams: {
        email: this.email
      }
    });
  }
}
