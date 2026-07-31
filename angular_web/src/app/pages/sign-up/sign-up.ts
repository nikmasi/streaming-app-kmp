import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  imports: [],
  templateUrl: './sign-up.html',
  styleUrl: './sign-up.css',
})
export class SignUp {

  router = inject(Router)

  sign_in(){
    this.router.navigate(['/signIn'])
  }

  sign_up(){ 
    this.router.navigate(['/home'])
  }
}
