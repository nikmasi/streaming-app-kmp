import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

  signIn() {
    console.log(this.email, this.password);
  }

}